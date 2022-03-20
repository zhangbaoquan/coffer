# Built application files

## 更新记录

学习笔记 ./gradlew clean -q

一、Gradle 生命周期

1、初始化阶段 -root project 收集本次参与构建的所有子工程，创建一个项目的层次结构，并且为每个项目创建一个Project实例，例如setting.gradle 文件

2、配置阶段 -project 执行各个目录下build.gradle 脚本，来完成Project对象的配置，并生成任务依赖图。从根目录的build.gradle文件开始执行

3、执行阶段 -task 根据配置阶段生成的任务依赖图，依次去执行

二、Gradle 插件

* 提供具体的构建功能（Task）
* 提高代码的复用性

三、Gradle 插件种类

* 二进制插件：即实现Project接口的类的封装（通常是复杂的，且可以被复用的），例如滴滴的SPI 插件、DoKit插件等（）
* 脚本插件：在工程里写的Gradle脚本，例如other.gradle 、t1.gradle

四、页面路由插件的开发 功能梳理

* 标记界面
* 收集界面
* 生成文档
* 注册映射

* 打开页面

五、实现自定义参数配置

* 定义 Extension
* 注册 Extension
* 使用 Extension
* 获取 Extension

六、发布与使用插件

* 发布到本地仓库
* 在工程中应用插件

七、APT
1、注解（Annotation）
描述数据的数据，是标注。可以在编译期间做代码检查，保证代码的正确性。
2、注解处理器（Annotation Processing Tool）
对标记的注解进行识别和处理。

八、页面路由
* 传递路径参数
* 生成json文件
* 汇总生成文档

