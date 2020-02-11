package ir.aliiz.domain.model

data class Resource<T>(val status: Status, val data: T?, val error: ErrorInfoDomain?) {
    enum class Status {
        ERROR, LOADING, SUCCESS
    }

    companion object {
        fun <T> success(value: T) = Resource(Status.SUCCESS, value, null)
        fun <T> error(error: ErrorInfoDomain) = Resource<T>(Status.ERROR, null, error)
        fun <T> loading() = Resource<T>(Status.LOADING, null, null)
    }
}

class ErrorInfoDomain(val e: Exception)