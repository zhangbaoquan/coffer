package com.kc.router.processor;


import com.google.auto.service.AutoService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kc.router.annotation.Destination;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.annotation.processing.Processor;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

/**
 * @author      : coffer
 * date         : 2022/3/18
 * description  : 使用AutoService 注册注解处理器
 */
@AutoService(Processor.class)
public class DestinationProcessor extends AbstractProcessor{
    private static final String TAG = "DestinationProcessor";

    /**
     * 告诉编译器，当前处理器支持的注解类型
     * @return 注解类型
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(
                Destination.class.getCanonicalName()
        );
    }

    /**
     * 编译器找到我们关心的注解后，会回调这个方法
     * @param set 注解
     * @param roundEnvironment 环境
     * @return 是否处理
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        // 避免多次调用 process
        if (roundEnvironment.processingOver()) {
            return false;
        }

        System.out.println(TAG + " >>> process start ...");

        // 获取在app 工程里的build.gradle中设置的kapt参数
        String rootDir = processingEnv.getOptions().get("root_project_dir");
        System.out.println(TAG + " >>> rootDir ..." + rootDir);

        // 1、获取所有标记了自定义注解@Destination 类的信息
        Set<Element> allDestinationElements = (Set<Element>) roundEnvironment
                .getElementsAnnotatedWith(Destination.class);
        if (allDestinationElements.size()<1){
            return false;
        }

        // 将要自动生成的类的类名，下面要生成的代码可以参考RouterMapping_1类
        String className = "RouterMapping_" + System.currentTimeMillis();
        StringBuilder builder = new StringBuilder();
        builder.append("package com.kc.mapping;\n\n");
        builder.append("import java.util.HashMap;\n");
        builder.append("import java.util.Map;\n\n");
        builder.append("public class ").append(className).append(" {\n\n");
        builder.append("    public static Map<String, String> get() {\n\n");
        builder.append("        Map<String, String> mapping = new HashMap<>();\n\n");

        // 将页面配置的注解信息写到json文件中，json最外层是数组
        final JsonArray destinationJsonArray = new JsonArray();

        // 2、遍历所有的@Destination 注解信息
        for (Element element : allDestinationElements){
            TypeElement typeElement = (TypeElement) element;
            // 获取当前类上的@Destination 注解信息
            Destination destination = typeElement.getAnnotation(Destination.class);
            if (destination == null){
                continue;
            }
            String url = destination.url();
            String description = destination.description();
            // 3、由于需要生成url对应的映射表，因此需要当前类的全类名
            String realPath = typeElement.getQualifiedName().toString();

            System.out.println(TAG + " >>> url = " + url);
            System.out.println(TAG + " >>> description = " + description);
            System.out.println(TAG + " >>> realPath = " + realPath);

            builder.append("        ")
                    .append("mapping.put(")
                    .append("\"" + url + "\"")
                    .append(", ")
                    .append("\"" + realPath + "\"")
                    .append(");\n");

            // 创建一个json对象，将信息放在对象中
            JsonObject item = new JsonObject();
            item.addProperty("url", url);
            item.addProperty("description",description);
            item.addProperty("realPath", realPath);
            destinationJsonArray.add(item);

        }

        builder.append("        return mapping;\n");
        builder.append("    }\n");
        builder.append("}\n");

        String mappingFullClassName = "com.kc.mapping." + className;

        System.out.println(TAG + " >>> mappingFullClassName = "
                + mappingFullClassName);

        System.out.println(TAG + " >>> class content = \n" + builder);
        // 写入自动生成的类到本地文件中
        try {
            JavaFileObject source = processingEnv.getFiler()
                    .createSourceFile(mappingFullClassName);
            Writer writer = source.openWriter();
            writer.write(builder.toString());
            writer.flush();
            writer.close();
        } catch (Exception ex) {
            throw new RuntimeException("Error while create file", ex);
        }

        // 写入JSON到本地文件中
        // 检测父目录是否存在
        File rootDirFile = new File(rootDir);
        if (!rootDirFile.exists()) {
            throw new RuntimeException("root_project_dir not exist!");
        }

        // 创建 router_mapping 子目录，用于保存生成的json文件
        File routerFileDir = new File(rootDirFile, "router_mapping");
        if (!routerFileDir.exists()) {
            routerFileDir.mkdir();
        }
        // 真正写入内容的json
        File mappingFile = new File(routerFileDir,
                "mapping_" + System.currentTimeMillis() + ".json");
        // 写入json内容
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(mappingFile));
            String jsonStr = destinationJsonArray.toString();
            out.write(jsonStr);
            out.flush();
            out.close();
        } catch (Throwable throwable) {
            throw new RuntimeException("Error while writing json", throwable);
        }
        System.out.println(TAG + " >>> process finish.");
        return false;
    }
}
