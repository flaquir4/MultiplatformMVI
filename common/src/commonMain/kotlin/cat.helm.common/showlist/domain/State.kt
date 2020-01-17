package cat.helm.common.showlist.domain

import cat.helm.common.showlist.domain.model.TvShow

sealed class State {
    object LoadingState : State()
    data class DataState(val shows: List<TvShow>) : State()
    data class ErrorState(val error: Throwable) : State()
}