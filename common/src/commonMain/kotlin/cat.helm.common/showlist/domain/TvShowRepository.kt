package cat.helm.common.showlist.domain

import cat.helm.common.showlist.domain.model.TvShow
import kotlinx.coroutines.flow.Flow
import cat.helm.common.showlist.Result


interface TvShowRepository {
    fun getShows(): Flow<Result<List<TvShow>, Exception>>
}