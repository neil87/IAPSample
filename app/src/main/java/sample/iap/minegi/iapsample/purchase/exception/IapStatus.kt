package sample.iap.minegi.iapsample.purchase.exception

enum class IapStatus(val code: Int, val message: String) {
    SERVICE_DISCONNECTED(0x0001, "Service disconnected");

    override fun toString(): String {
        return "code = $code, message = $message"
    }
}