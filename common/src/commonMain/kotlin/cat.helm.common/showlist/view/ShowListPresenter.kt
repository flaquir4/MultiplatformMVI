package cat.helm.common.showlist.view

import cat.helm.common.showlist.domain.ShowListFeature
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@FlowPreview
@ExperimentalCoroutinesApi
class ShowListPresenter(view: ShowListView, private val feature: ShowListFeature) {

    val state = feature.state

    fun doIntent(userIntent: ShowListView.UserIntent) {
        feature.doIntent(userIntent)
    }
}