package com.coffer.router.gradle

import java.util.jar.JarEntry
import java.util.jar.JarFile
/**
 * 收集目标类
 */
class RouterMappingCollector {

    /**
     * 这里的包名是我们自动生成的RouterMapping_xxx 文件所在包名！
     */
    private static final String PACKAGE_NAME = 'com/kc/mapping'
    /**
     * 类的前缀
     */
    private static final String CLASS_NAME_PREFIX = 'RouterMapping_'
    private static final String CLASS_FILE_SUFFIX = '.class'

    private final Set<String> mappingClassNames = new HashSet<>()
    /**
     * 获取收集好的映射表类名
     * @return
     */
    Set<String> getMappingClassName() {
        return mappingClassNames;
    }

    /**
     * 收集class文件或者class文件目录中的映射表类。
     * @param classFile
     */
    void collect(File classFile) {
        if (classFile == null || !classFile.exists()) return
        if (classFile.isFile()) {
            // 是文件，找到我们前面限定的目标类
            if (classFile.absolutePath.contains(PACKAGE_NAME)
                    && classFile.name.startsWith(CLASS_NAME_PREFIX)
                    && classFile.name.endsWith(CLASS_FILE_SUFFIX)) {
                String className =
                        classFile.name.replace(CLASS_FILE_SUFFIX, "")
                // 将目标文件的类名放到集合中
                mappingClassNames.add(className)
            }
        } else { // 是目录，则递归
            classFile.listFiles().each {
                collect(it)
            }
        }
    }

    /**
     * 收集JAR包中的目标映射类
     * @param jarFile
     */
    void collectFromJarFile(File jarFile) {
        Enumeration enumeration = new JarFile(jarFile).entries()

        while (enumeration.hasMoreElements()) {
            JarEntry jarEntry = (JarEntry)enumeration.nextElement()
            // 文件名称
            String entryName = jarEntry.getName()
            if (entryName.contains(PACKAGE_NAME)
                    && entryName.contains(CLASS_NAME_PREFIX)
                    && entryName.contains(CLASS_FILE_SUFFIX)) {
                String className = entryName
                        .replace(PACKAGE_NAME, "")
                        .replace("/", "")
                        .replace(CLASS_FILE_SUFFIX, "")

                mappingClassNames.add(className)
            }
        }

    }
}