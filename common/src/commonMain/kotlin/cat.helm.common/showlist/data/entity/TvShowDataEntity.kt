package cat.helm.common.showlist.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class TvShowDataEntity(
    val popularity: Float,
    val id: Int,
    val name: String,
    val backdrop_path: String?,
    val overview: String
)