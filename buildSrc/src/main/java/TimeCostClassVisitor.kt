import org.gradle.internal.impldep.org.objectweb.asm.ClassVisitor
import org.gradle.internal.impldep.org.objectweb.asm.MethodVisitor
import org.gradle.internal.impldep.org.objectweb.asm.Opcodes
import org.gradle.internal.impldep.org.objectweb.asm.commons.AdviceAdapter

/**
 * @author liguandong
 * @data 2022/10/8
 *
 */
class TimeCostClassVisitor(nextVisitor: ClassVisitor) : ClassVisitor(Opcodes.ASM7, nextVisitor) {
    override fun visitMethod(
        access: Int, name: String?, descriptor: String?, signature: String?, exceptions: Array<out String>?
    ): MethodVisitor {
        val methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions)
        val newMethodVisitor =
            object : AdviceAdapter(Opcodes.ASM7, methodVisitor, access, name, descriptor) {
                @Override
                override fun onMethodEnter() {
                    // 方法开始
                    if (isNeedVisiMethod(name)) {
                        mv.visitLdcInsn(name);
                        mv.visitMethodInsn(
                            INVOKESTATIC, "com/zj/android_asm/TimeCache", "putStartTime","(Ljava/lang/String;)V", false
                        );
                    }
                    super.onMethodEnter();
                }

                @Override
                override fun onMethodExit(opcode: Int) {
                    // 方法结束
                    if (isNeedVisiMethod(name)) {
                        mv.visitLdcInsn(name);
                        mv.visitMethodInsn(
                            INVOKESTATIC, "com/zj/android_asm/TimeCache", "putEndTime","(Ljava/lang/String;)V", false
                        );
                    }
                    super.onMethodExit(opcode);
                }
            }
        return newMethodVisitor
    }

    private fun isNeedVisiMethod(name: String?):Boolean {
        return name != "putStartTime" && name != "putEndTime" && name != "<clinit>" && name != "printlnTime" && name != "<init>"
    }
}