package cat.helm.common.showlist.data.network

import cat.helm.common.showlist.Result
import io.ktor.client.response.HttpResponse
import io.ktor.client.response.readText
import io.ktor.http.HttpStatusCode

fun HttpResponse.isSuccess(): Boolean = this.status.value in 200..299

suspend fun <T> HttpResponse.handleResponse(onSuccess: (String) -> Result<T, Exception>) =
    if (isSuccess()) {
        onSuccess(readText())
    } else {
        when (this.status) {
            HttpStatusCode.Unauthorized -> Result.Failure(NetworkException.UnauthorizedException())
            else -> Result.Failure(NetworkException.ServerErrorException())
        }
    }