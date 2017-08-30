package sample.iap.minegi.iapsample.purchase.exception

class IAPException(val errorCode: IAPErrorCode) : Throwable(errorCode.message)