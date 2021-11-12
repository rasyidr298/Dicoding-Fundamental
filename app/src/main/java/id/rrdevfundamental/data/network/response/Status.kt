package id.rrdevfundamental.data.network.response

class Status<T>(val status: StatusType, val data: T?, val message: String?) {

    enum class StatusType {
        START, SUCCESS, ERROR
    }

    companion object {
        fun <T> start(message: String?, data: T?): Status<T> {
            return Status(StatusType.START, data, message)
        }

        fun <T> success(data: T?): Status<T> {
            return Status(StatusType.SUCCESS, data, null)
        }

        fun <T> error(message: String?, data: T?): Status<T> {
            return Status(StatusType.ERROR, data, message)
        }
    }
}