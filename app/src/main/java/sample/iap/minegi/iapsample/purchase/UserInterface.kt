package sample.iap.minegi.iapsample.purchase

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment

class UserInterface(val activity: Activity) {
    constructor(fragment: Fragment) : this(fragment.activity) {
        this.fragment = fragment
    }

    var fragment: Fragment? = null

    val context: Context?
        get() = if (fragment != null) {
            checkNotNull(fragment).context
        } else {
            activity
        }
}