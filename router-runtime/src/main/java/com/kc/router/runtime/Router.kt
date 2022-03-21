package com.kc.router.runtime

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log

/**
 * 将编译期间生成的路由映射表加载到内存中
 */
object Router {


    private const val TAG = "RouterTAG"

    // 编译期间生成的总映射表，对应RouterMappingByteCodeBuilder类中的CLASS_NAME
    private const val GENERATED_MAPPING =
        "com.kc.router.mapping.generated.RouterMapping"

    // 存储所有映射表信息
    private val mapping: HashMap<String, String> = HashMap()

    fun init() {

        try {
            val clazz = Class.forName(GENERATED_MAPPING)
            val method = clazz.getMethod("get")
            val allMapping = method.invoke(null) as Map<String, String>

            if (allMapping.isNotEmpty()) {
                Log.i(TAG, "init: get all mapping:")
                allMapping.onEach {
                    Log.i(TAG, "    ${it.key} -> ${it.value}")
                }
                mapping.putAll(allMapping)
            }

        } catch (e: Throwable) {
            Log.e(TAG, "init: error while init router : $e")
        }

    }

    fun go(context: Context, url: String) {

        if (context == null || url == null) {
            Log.i(TAG, "go: param error.")
            return
        }

        // 匹配URL，找到目标页面
        // 解析URL里的参数，封装成一个Bundle
        // 打开对应的Activity，并传入参数


        // 1、解析例如router://imooc/profile?name=imooc&message=hello
        val uri = Uri.parse(url)
        // 对应router
        val scheme = uri.scheme
        // 对应imooc
        val host = uri.host
        // 对应/profile
        val path = uri.path

        var targetActivityClass = ""

        mapping.onEach {
            val ruri = Uri.parse(it.key)
            val rscheme = ruri.scheme
            val rhost = ruri.host
            val rpath = ruri.path

            if (rscheme == scheme && rhost == host && rpath == path) {
                targetActivityClass = it.value
            }
        }

        if (targetActivityClass == "") {
            Log.e(TAG, "go:     no destination found.")
            return
        }

        // 解析URL里的参数，封装成为一个 Bundle

        val bundle = Bundle()
        val query = uri.query
        query?.let {
            if (it.length >= 3) {
                val args = it.split("&")
                args.onEach { arg ->
                    val splits = arg.split("=")
                    bundle.putString(splits[0], splits[1])
                }
            }
        }


        // 打开对应的Activity，并传入参数

        try {
            val activity = Class.forName(targetActivityClass)
            val intent = Intent(context, activity)
            intent.putExtras(bundle)
            context.startActivity(intent)
        } catch (e : Throwable) {
            Log.e(TAG, "go: error while start activity: $targetActivityClass, " +
                    "e = $e")
        }
    }
}