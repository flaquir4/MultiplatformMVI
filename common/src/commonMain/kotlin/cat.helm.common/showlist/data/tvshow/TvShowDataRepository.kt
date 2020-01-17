package cat.helm.common.showlist.data.tvshow

import cat.helm.common.showlist.Result
import cat.helm.common.showlist.data.entity.mapper.mapToTvShow
import cat.helm.common.showlist.domain.TvShowRepository
import cat.helm.common.showlist.domain.model.TvShow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@ExperimentalCoroutinesApi
class TvShowDataRepository(
    private val tvShowApiDataSource: TvShowApiDataSource,
    private val tvShowCacheDatasource: TvShowCacheDatasource
) : TvShowRepository {

    override  fun getShows(): Flow<Result<List<TvShow>, Exception>> =
        tvShowApiDataSource.getShows().map { it.map { it.map { it.mapToTvShow() } } }
}
