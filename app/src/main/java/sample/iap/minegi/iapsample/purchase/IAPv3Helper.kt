package sample.iap.minegi.iapsample.purchase

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import com.android.vending.billing.IInAppBillingService
import io.reactivex.Single
import io.reactivex.Single.create
import sample.iap.minegi.iapsample.BuildConfig
import sample.iap.minegi.iapsample.purchase.exception.IAPErrorCode
import sample.iap.minegi.iapsample.purchase.exception.IAPException
import sample.iap.minegi.iapsample.purchase.model.DummyValue

class IAPv3Helper(private val ui: UserInterface) {
    private val context: Context = checkNotNull(ui.context)
    private var serviceConnection: ServiceConnection? = null
    private var inAppBillingService: IInAppBillingService? = null

    private fun uiName(): String {
        return if (ui.fragment != null) {
            checkNotNull(ui.fragment).javaClass.simpleName
        } else {
            checkNotNull(ui.activity).javaClass.simpleName
        }
    }

    private fun connectService(): Single<DummyValue> {
        return create<DummyValue> { emitter ->
            if (true) {
                emitter.onSuccess(DummyValue.value)
            } else {
                serviceConnection = object : ServiceConnection {
                    override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
                        log("Service connected")
                        inAppBillingService = IInAppBillingService.Stub.asInterface(binder)
                        emitter.onSuccess(DummyValue.value)
                    }

                    override fun onServiceDisconnected(name: ComponentName?) {
                        log("Service disconnected")
                        inAppBillingService = null
                        emitter.onError(IAPException(IAPErrorCode.SERVICE_DISCONNECTED))
                    }
                }
            }
        }
    }

    private fun log(message: String) {
        if (BuildConfig.DEV_MODE)
            Log.d(uiName(), message)
    }
}