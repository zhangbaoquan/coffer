package com.coffer.router.gradle

import com.android.build.api.transform.Transform
import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import groovy.json.JsonSlurper
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * 下面关于 " 自动帮助用户传递路径参数到注解处理器中 "的实现效果，相当于在app 工程里的gradle文件设置的
 * kapt {arguments {arg("root_project_dir",rootProject.projectDir.absolutePath)}}*/
class CofferRouterPlugin implements Plugin<Project> {

    // 实现apply方法，注入插件的逻辑
    void apply(Project project) {
        println("my plugin router apply from : ${project.name}")
        // 注册 自定义Transform
        if (project.plugins.hasPlugin(AppPlugin)) {
            AppExtension appExtension = project.extensions.getByType(AppExtension)
            Transform transform = new RouterMappingTransform()
            appExtension.registerTransform(transform)
        }

        // 1. 自动帮助用户传递路径参数到注解处理器中
        if (project.extensions.findByName("kapt") != null) {
            project.extensions.findByName("kapt").arguments {
                arg("root_project_dir", project.rootProject.projectDir.absolutePath)
            }
        }
        // 2. 实现旧的构建产物的自动清理
        project.clean.doFirst {
            // 在clean任务开始执行之前，删除 上一次构建生成的 router_mapping目录
            File routerMappingDir =
                    new File(project.rootProject.projectDir, "router_mapping")

            if (routerMappingDir.exists()) {
                routerMappingDir.deleteDir()
            }
        }

        // 汇总WIKI的逻辑只需要执行一次就行
        if (!project.plugins.hasPlugin(AppPlugin)) {
            return
        }

        // 注册自定义Extension
        project.getExtensions().create("router", RouterExtension)

        // afterEvaluate 表示当前工程配置阶段已经结束
        project.afterEvaluate {
            // 获取自定义Extension
            RouterExtension extension = project["router"]
            println("用户设置的WIKI路径为 ： ${extension.wikiDir}")

            // 3. 在javac任务 (compileDebugJavaWithJavac) 后，汇总生成文档
            project.tasks.findAll { task ->
                task.name.startsWith("compile") && task.name.endsWith("JavaWithJavac")
            }.each {task ->
                task.doLast {

                    File routerMappingDir =
                            new File(project.rootProject.projectDir,
                                    "router_mapping")

                    if (!routerMappingDir.exists()) {
                        return
                    }
                    // 获取根目录下router_mapping 文件夹下的所有json文件
                    File[] allChildFiles = routerMappingDir.listFiles()

                    if (allChildFiles.length < 1) {
                        return
                    }

                    StringBuilder markdownBuilder = new StringBuilder()
                    // # 是MarkDown 语法
                    markdownBuilder.append("# 页面文档\n\n")
                    allChildFiles.each { child ->
                        if (child.name.endsWith(".json")) {
                            // JsonSlurper 是json解析工具
                            JsonSlurper jsonSlurper = new JsonSlurper()
                            def content = jsonSlurper.parse(child)
                            // 由于我们的json文件最外层是数组，因此需要遍历
                            content.each { innerContent ->
                                // 这里innerContent是数组里的对象
                                def url = innerContent['url']
                                def description = innerContent['description']
                                def realPath = innerContent['realPath']

                                markdownBuilder.append("## $description \n")
                                markdownBuilder.append("- url: $url \n")
                                markdownBuilder.append("- realPath: $realPath \n\n")
                            }
                        }
                    }
                    // 设置用户传入的WiKi目录为MarkDown 所在位置目录
                    File wikiFileDir = new File(extension.wikiDir)
                    if (!wikiFileDir.exists()) {
                        wikiFileDir.mkdir()
                    }
                    File wikiFile = new File(wikiFileDir, "页面文档.md")
                    if (wikiFile.exists()) {
                        wikiFile.delete()
                    }
                    // 将MarkDown 写入用户传入的WiKi目录
                    wikiFile.write(markdownBuilder.toString())
                }
            }
        }
    }

}