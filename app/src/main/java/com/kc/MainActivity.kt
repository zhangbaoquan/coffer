package com.kc

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.coffer.app.R
import com.kc.router.annotation.Destination
import com.kc.router.runtime.Router

@Destination(
    url = "router://page-home",
    description = "应用主页"
)
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 输出渠道信息
        val appInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
        val channelName = appInfo.metaData.getString("MTA_CHANNEL")
        Log.d("channel_tag","channel = $channelName")

        findViewById<View>(R.id.btn).setOnClickListener { v ->
            Router.go(
                v.context,
                "router://page-Test/tt?name=imooc&message=hello"
            )
        }
    }
}