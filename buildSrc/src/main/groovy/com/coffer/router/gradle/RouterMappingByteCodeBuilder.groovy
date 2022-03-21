package com.coffer.router.gradle

import org.objectweb.asm.ClassWriter
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * 路由映射字节码创建者
 */
class RouterMappingByteCodeBuilder implements Opcodes {

    /**
     * 最终要生成的类名
     */
    public static final String CLASS_NAME =
            "com/kc/router/mapping/generated/RouterMapping"

    /**
     *
     * @param allMappingNames 所有映射表的类名
     * @return
     */
    static byte[] get(Set<String> allMappingNames) {
        // 1. 创建一个类
        // 2. 创建构造方法
        // 3. 创建get方法
        //   （1）创建一个Map
        //   （2）塞入所有映射表的内容
        //   （3）返回map

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS)
        // 创建一个类
        cw.visit(V1_7,
                ACC_PUBLIC + ACC_SUPER,
                CLASS_NAME,
                null,
                "java/lang/Object",
                null
        )

        // 生成或者编辑方法
        MethodVisitor mv

        // 创建构造方法
        mv = cw.visitMethod(Opcodes.ACC_PUBLIC,
                "<init>",
                "()V",
                null,
                null)
        // 开启字节码的生成与访问
        mv.visitCode()
        mv.visitVarInsn(Opcodes.ALOAD, 0)
        // 调用父类的构造方法
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL,
                "java/lang/Object", "<init>", "()V", false)
        mv.visitInsn(Opcodes.RETURN)
        mv.visitMaxs(1, 1)
        mv.visitEnd()

        // 创建get方法
        mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC,
                "get",
                "()Ljava/util/Map;",
                "()Ljava/util/Map<Ljava/lang/String;Ljava/lang/String;>;",
                null)
        // 开启字节码的生成与访问
        mv.visitCode()

        mv.visitTypeInsn(NEW, "java/util/HashMap")
        // 入参
        mv.visitInsn(DUP)
        // 调用构造方法
        mv.visitMethodInsn(INVOKESPECIAL,
                "java/util/HashMap", "<init>", "()V", false)
        mv.visitVarInsn(ASTORE, 0)

        // 向Map中，逐个塞入所有映射表的内容
        allMappingNames.each {

            mv.visitVarInsn(ALOAD, 0)
            mv.visitMethodInsn(INVOKESTATIC,
                    "com/kc/mapping/$it",
                    "get", "()Ljava/util/Map;", false)
            mv.visitMethodInsn(INVOKEINTERFACE,
                    "java/util/Map",
                    "putAll",
                    "(Ljava/util/Map;)V", true)
        }

        // 返回map
        mv.visitVarInsn(ALOAD, 0)
        mv.visitInsn(ARETURN)
        mv.visitMaxs(2, 2)

        mv.visitEnd()

        return cw.toByteArray()
    }
}