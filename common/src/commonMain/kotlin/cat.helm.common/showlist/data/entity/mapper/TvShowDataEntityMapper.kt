package cat.helm.common.showlist.data.entity.mapper

import cat.helm.common.showlist.data.entity.TvShowDataEntity
import cat.helm.common.showlist.domain.model.TvShow

fun TvShowDataEntity.mapToTvShow() = TvShow(this.name, this.overview, this.backdrop_path ?: "")
