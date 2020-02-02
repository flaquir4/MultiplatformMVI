package cat.helm.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cat.helm.common.showlist.domain.State
import cat.helm.common.showlist.view.ShowListPresenterFactory
import cat.helm.common.showlist.view.ShowListView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_tv_show.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity(), ShowListView {
    lateinit var adapter: TvShowAdapter
    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_show)
        setSupportActionBar(toolbar)
        adapter = TvShowAdapter()

        val presenter = ShowListPresenterFactory.create()

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
