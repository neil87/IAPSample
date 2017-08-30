package sample.iap.minegi.iapsample.purchase

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import com.android.vending.billing.IInAppBillingService
import io.reactivex.Single
import io.reactivex.Single.create
import sample.iap.minegi.iapsample.BuildConfig
import sample.iap.minegi.iapsample.purchase.IapConstants.IAP_BIND_INTENT_ACTION
import sample.iap.minegi.iapsample.purchase.IapConstants.IAP_VENDING_PACKAGE_NAME
import sample.iap.minegi.iapsample.purchase.exception.IapStatus.SERVICE_DISCONNECTED
import sample.iap.minegi.iapsample.purchase.exception.IapException
import sample.iap.minegi.iapsample.purchase.model.DummyValue

class IapHelperV3(private val ui: UserInterface) {
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
            if (serviceConnection != null) {
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
                        emitter.onError(IapException(SERVICE_DISCONNECTED))
                    }
                }
                bindService()
            }
        }
    }

    private fun bindService() {
        log("bindService")
        val intent = Intent(IAP_BIND_INTENT_ACTION)
        intent.`package` = IAP_VENDING_PACKAGE_NAME
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun unbindService() {
        log("unbindService")
        inAppBillingService?.let {
            context.unbindService(serviceConnection)
            inAppBillingService = null
        }
    }

    fun isBillingSupported()

    private fun log(message: String) {
        if (BuildConfig.DEV_MODE) {
            Log.d(uiName(), message)
        }
    }
}