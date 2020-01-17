package cat.helm.common.showlist.data.tvshow

import cat.helm.common.showlist.Result
import cat.helm.common.showlist.data.entity.TvShowDataEntity

class TvShowCacheDatasource {

    var tvShowList: List<TvShowDataEntity> = mutableListOf()

    fun save(shows: List<TvShowDataEntity>) {
        tvShowList = shows
    }

    fun getShowById(showId: Int): Result<TvShowDataEntity, Exception> = Result.of {
        tvShowList.find { it.id == showId }
    }
}