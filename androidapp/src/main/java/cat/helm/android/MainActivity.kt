package cat.helm.android

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import cat.helm.common.showlist.data.network.ApiConstants
import cat.helm.common.showlist.data.tvshow.TvShowApiDataSource
import cat.helm.common.showlist.data.tvshow.TvShowCacheDatasource
import cat.helm.common.showlist.data.tvshow.TvShowDataRepository
import cat.helm.common.showlist.domain.ShowListFeature
import cat.helm.common.showlist.domain.State
import cat.helm.common.showlist.domain.usecase.GetShows
import cat.helm.common.showlist.view.ShowListPresenter
import cat.helm.common.showlist.view.ShowListView
import io.ktor.client.HttpClient
import io.ktor.client.features.defaultRequest
import io.ktor.client.request.host
import io.ktor.http.URLProtocol
import kotlinx.android.synthetic.main.activity_main.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.android.synthetic.main.activity_tv_show.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity(), ShowListView {
    lateinit var adapter: TvShowAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_show)
        setSupportActionBar(toolbar)
        adapter = TvShowAdapter()

        val presenter = ShowListPresenter(this, ShowListFeature(GetShows(TvShowDataRepository(TvShowApiDataSource(
            HttpClient().config {
                defaultRequest {
                    host = ApiConstants.BASE_URL
                    url {
                        protocol = URLProtocol.HTTPS
                        parameters["api_key"] = ApiConstants.API_KEY
                    }
                }
                install(JsonFeature) {
                    serializer = KotlinxSerializer()
                }
            }
        ),
            TvShowCacheDatasource()
        ))))

        // fab.setOnClickListener {
        presenter.doIntent(ShowListView.UserIntent.GetShowList)
        //    TvShowAdapter.kt  }

        presenter
            .state
            .onEach {
                render(it)
            }
            .launchIn(MainScope())


    }

    override fun render(state: State) {
        when (state) {
            is State.DataState -> {
                show_list?.apply {
                    layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this@MainActivity)
                    adapter = this@MainActivity.adapter
                }
                this.adapter.tvShows = state.shows
            }
        }
    }
}
