package cat.helm.common.showlist.domain.usecase

import cat.helm.common.showlist.Result
import cat.helm.common.showlist.domain.TvShowRepository
import cat.helm.common.showlist.domain.model.TvShow
import kotlinx.coroutines.flow.Flow

class GetShows(private val tvShowRepository: TvShowRepository) {
    operator fun invoke(): Flow<Result<List<TvShow>, Exception>> = tvShowRepository.getShows()
}