package asm

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.commons.AdviceAdapter

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
        val methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions)
        val newMethodVisitor =
            object : AdviceAdapter(Opcodes.ASM7, methodVisitor, access, name, descriptor) {
                @Override
                override fun onMethodEnter() {
                    // 方法开始
                    if (isNeedVisiMethod(name)) {
                        visitMethodInsn(
                            INVOKESTATIC,
                            "com/cs/commonlib/TimeCostUtils",
                            "onMethodEnter",
                            "()V",
                            false
                        );
                    }
                    super.onMethodEnter();
                }

                @Override
                override fun onMethodExit(opcode: Int) {
                    // 方法结束
                    if (isNeedVisiMethod(name)) {
                        visitLdcInsn(className);
                        visitLdcInsn(name);
                        visitMethodInsn(
                            INVOKESTATIC,
                            "com/cs/commonlib/TimeCostUtils",
                            "onMethodEnd",
                            "(Ljava/lang/String;Ljava/lang/String;)V",
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