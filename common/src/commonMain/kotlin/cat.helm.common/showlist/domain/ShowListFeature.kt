package cat.helm.common.showlist.domain

import cat.helm.common.showlist.domain.usecase.GetShows
import cat.helm.common.showlist.view.ShowListView
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import kotlin.properties.Delegates


@FlowPreview
@ExperimentalCoroutinesApi
class ShowListFeature(val getShows: GetShows) {


    private val channel = ConflatedBroadcastChannel<State>(State.LoadingState)

    val state: Flow<State> = channel.asFlow()

    private var _state: State by Delegates.observable<State>(State.LoadingState) { _, _, new ->
        channel.offer(new)
    }

    fun doIntent(userIntent: ShowListView.UserIntent) {
        when (userIntent) {
            is ShowListView.UserIntent.GetShowList -> showList()
        }
    }


    private fun showList() {
        getShows()
            .onEach {
                it.success {
                    _state = State.DataState(it)
                }
                it.failure {
                    println(it)
                }
            }
            .catch {
                println("miau")
            }.onCompletion {
                println("pastel")
            }
            .launchIn(MainScope())
    }
}