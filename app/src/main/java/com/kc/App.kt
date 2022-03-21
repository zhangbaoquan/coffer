package com.kc

import android.app.Application
import com.kc.router.runtime.Router

/**
 * author       : coffer
 * date         : 2022/3/21
 * description  :
 */

class App :Application(){

    override fun onCreate() {
        super.onCreate()
        Router.init()
    }
}