package com.kc.biz_reading

import android.app.Activity
import com.kc.router.annotation.Destination

/**
 * author       : coffer
 * date         : 2022/3/20
 * description  :
 */

@Destination(
    url = "router://reading",
    description = "阅读页"
)
class ReadingActivity : Activity() {

}