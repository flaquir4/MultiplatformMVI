package cat.helm.common.showlist.data.network

sealed class NetworkException(message: String?) : Exception(message) {
    data class UnauthorizedException(override val message: String? = null) : NetworkException(message)
    data class ServerErrorException(override val message: String? = null) : NetworkException(message)
}