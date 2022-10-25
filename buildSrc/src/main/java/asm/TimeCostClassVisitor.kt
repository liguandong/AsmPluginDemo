package asm

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import org.objectweb.asm.commons.AdviceAdapter
import org.objectweb.asm.commons.Method

/**
 * @author liguandong
 * @data 2022/10/8
 *
 */
class TimeCostClassVisitor(nextVisitor: ClassVisitor) : ClassVisitor(Opcodes.ASM7, nextVisitor) {
    private var className: String? = null
    override fun visit(
        version: Int,
        access: Int,
        name: String?,
        signature: String?,
        superName: String?,
        interfaces: Array<out String>?
    ) {
        super.visit(version, access, name, signature, superName, interfaces)
        className = name
    }

    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        println("$className ${name} ${descriptor} visitMethod")
        val methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions)
        val newMethodVisitor =
            object : AdviceAdapter(Opcodes.ASM7, methodVisitor, access, name, descriptor) {
                private var slot = 0
                @Override
                override fun onMethodEnter() {
                    // 方法开始
//                    println("$className ${name} ${descriptor} onMethodEnter")
                    if (isNeedVisiMethod(name)) {
                        println("$className ${name} ${descriptor} onMethodEnter true")
                        //调用静态方法，并返回一个值压栈
                        invokeStatic(Type.getType("Ljava/lang/System;"), Method("currentTimeMillis","()J"))
                        //创建一个本地变量，返回一个索引
                        slot = newLocal(Type.LONG_TYPE)
                        //栈顶元素复制到一个局部变量
                        storeLocal(slot)
                    }
                    super.onMethodEnter();
                }

                @Override
                override fun onMethodExit(opcode: Int) {
                    // 方法结束
//                    println("$className ${name} ${descriptor} onMethodExit")
                    if (isNeedVisiMethod(name)) {
                        println("$className ${name} ${descriptor} onMethodExit true")
                        visitLdcInsn(className);
                        visitLdcInsn(name);
                        loadLocal(slot)
//                        visitVarInsn(LLOAD, 1);
                        visitMethodInsn(
                            INVOKESTATIC,
                            "com/cs/commonlib/TimeCostUtils",
                            "onMethodEnd",
                            "(Ljava/lang/String;Ljava/lang/String;J)V",
                            false
                        );
                    }
                    super.onMethodExit(opcode);
                }
            }
        return newMethodVisitor
    }

    private fun isNeedVisiMethod(name: String?): Boolean {
        return name != "<clinit>" && name != "<init>"
    }
}