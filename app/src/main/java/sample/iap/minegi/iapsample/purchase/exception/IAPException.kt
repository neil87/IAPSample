package sample.iap.minegi.iapsample.purchase.exception

class IapException(val status: IapStatus) : Throwable(status.message)