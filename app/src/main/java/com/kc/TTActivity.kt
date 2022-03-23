package com.kc

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import com.kc.router.annotation.Destination

/**
 * author       : coffer
 * date         : 2022/3/23
 * description  :
 */
@Destination(url = "router://page-Test/tt", description = "测试页")
class TTActivity :Activity(){

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val textView = TextView(this)
        textView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        textView.setBackgroundColor(Color.WHITE)
        textView.setTextColor(Color.BLACK)
        textView.textSize = 16f
        textView.gravity = Gravity.CENTER

        setContentView(textView)

        val name = intent.getStringExtra("name")
        val message = intent.getStringExtra("message")

        textView.text = "tt -> $name : $message"
    }

}