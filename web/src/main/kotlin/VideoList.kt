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
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.host
import io.ktor.http.URLProtocol
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.css.*
import kotlinx.css.properties.LineHeight
import react.*
import react.dom.*
import style.lg
import style.md
import styled.*
import kotlin.math.round

interface VideoListState : RState, ShowListView {
    var videos: List<Video>
    var selectedVideo: Video?
    var onSelectVideo: (Video) -> Unit
    var viewState: State

    override fun render(state: State) {
        println("rendering")
        this.viewState = state
    }
}

class VideoList() : RComponent<RProps, VideoListState>() {

    override fun VideoListState.init() {
        val presenter =  ShowListPresenterFactory.create(this)

        presenter.doIntent(ShowListView.UserIntent.GetShowList)
        presenter.state.onEach {
            setState {
                viewState = it
            }
        }.launchIn(MainScope())
    }

    override fun RBuilder.render() {
        when (val currentState = state.viewState) {
            is State.DataState -> {
                styledDiv {
                    css {
                        display = Display.flex
                        flexWrap = FlexWrap.wrap
                    }
                    for (video in currentState.shows) {
                        styledDiv {
                            css {
                                display = Display.flex
                                md {
                                    paddingLeft = 3.rem
                                    paddingRight = 3.rem
                                }
                                lg{
                                    width = 40.pct
                                }
                            }
                            styledImg(
                                src = "https://image.tmdb.org/t/p/w500${video.image}"
                            ) {
                                css {
                                    objectFit = ObjectFit.cover
                                    width = 150.px
                                    md {
                                        width = 200.px
                                    }
                                }
                            }
                            div {
                                styledH3 {
                                    css {
                                        fontWeight = FontWeight.bold
                                        padding(.5.rem)
                                    }
                                    +video.name
                                }
                                styledP {
                                    css {
                                        put("line-height", "1.5em")
                                        height = 3.em
                                        overflow = Overflow.hidden
                                        md{
                                            height = 4.5.em
                                        }
                                        paddingLeft = .5.rem
                                    }
                                    key = video.name

                                    +video.overview
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun RBuilder.videoList(handler: VideoListState.() -> Unit): ReactElement {
    return child(VideoList::class) {
    }
}