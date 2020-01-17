package cat.helm.common.showlist.data.tvshow

import cat.helm.common.showlist.Result
import cat.helm.common.showlist.data.entity.TvShowDataEntity
import cat.helm.common.showlist.data.network.ApiConstants
import cat.helm.common.showlist.data.network.handleResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.response.HttpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.list

class TvShowApiDataSource(private val client: HttpClient) {

    fun getShows(): Flow<Result<List<TvShowDataEntity>, Exception>> =
        flow<Result<List<TvShowDataEntity>, Exception>> {
            val json = Json(JsonConfiguration.Stable.copy(strictMode = false))

            val response = client.get<HttpResponse> { url { encodedPath = ApiConstants.TV_SHOWS } }
            emit(response.handleResponse { responseBody ->
                Result.of {
                    val parser = json.parseJson(responseBody).jsonObject["results"] as JsonArray
                    json.parse(TvShowDataEntity.serializer().list, parser.toString())
                }
            })
        }.flowOn(Dispatchers.Unconfined)

    suspend fun getShowById(showId: Int): Result<TvShowDataEntity, Exception> {
        val response = client.get<HttpResponse> {
            url {
                encodedPath = ApiConstants.TV_SHOWS_BY_ID.replace("{id}", "$showId")
            }
        }

        return response.handleResponse { responseBody ->
            Result.of {
                Json.nonstrict.parse(TvShowDataEntity.serializer(), responseBody)
            }
        }
    }
}