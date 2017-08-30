package sample.iap.minegi.iapsample.purchase.exception

enum class IAPErrorCode(val code: Int, val message: String) {
    SERVICE_DISCONNECTED(0x0001, "Service disconnected")
}