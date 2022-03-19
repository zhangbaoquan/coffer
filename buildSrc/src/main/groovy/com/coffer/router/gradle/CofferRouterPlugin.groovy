package com.coffer.router.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class CofferRouterPlugin implements Plugin<Project> {

    // 实现apply方法，注入插件的逻辑
    void apply(Project project) {
        println("my plugin router apply from : ${project.name}")
        // 注册自定义Extension
        project.getExtensions().create("router",RouterExtension)

        // afterEvaluate 表示当前工程配置阶段已经结束
        project.afterEvaluate {
            // 获取自定义Extension
            RouterExtension extension = project["router"]
            println("用户设置的WIKI路径为 ： ${extension.wikiDir}")
        }
    }

}