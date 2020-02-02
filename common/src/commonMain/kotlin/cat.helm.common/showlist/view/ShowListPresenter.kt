package cat.helm.common.showlist.view

import cat.helm.common.showlist.domain.ShowListFeature
import cat.helm.common.showlist.domain.ShowListFeatureFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class ShowListPresenter(view: ShowListView, private val feature: ShowListFeature) {

    val state = feature.state

    fun doIntent(userIntent: ShowListView.UserIntent) {
        feature.doIntent(userIntent)
    }
}

object ShowListPresenterFactory {
    @FlowPreview
    @ExperimentalCoroutinesApi
    fun create(view: ShowListView): ShowListPresenter {
        return ShowListPresenter(view, ShowListFeatureFactory.create())
    }
}