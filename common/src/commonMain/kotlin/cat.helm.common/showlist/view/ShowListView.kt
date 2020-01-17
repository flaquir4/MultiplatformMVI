package cat.helm.common.showlist.view

import cat.helm.common.showlist.domain.State


interface ShowListView {

    fun render(state: State)

    sealed class UserIntent {
        object GetShowList : UserIntent()
    }


}