package pro.midev.obninsknamehuawei.ui.utils

import android.content.Intent
import com.huawei.hms.push.HmsMessageService
import timber.log.Timber

class HuaweiPushService : HmsMessageService() {

    override fun onNewToken(token: String?) {
        super.onNewToken(token)
        Timber.d("MY NEW TOKEN " + token)
        token?.let { sendTokenToDisplay(token) }
    }

    private fun sendTokenToDisplay(token: String) {
        val intent = Intent("com.huawei.push.codelab.ON_NEW_TOKEN")
        intent.putExtra("token", token)
        sendBroadcast(intent)
    }
}