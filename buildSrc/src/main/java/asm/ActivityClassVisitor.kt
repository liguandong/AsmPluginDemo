package asm

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.FieldVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.commons.AdviceAdapter


/**
 * @author liguandong
 * @data 2022/10/11
 *
 */
class ActivityClassVisitor(nextClassVisitor: ClassVisitor) :
    ClassVisitor(Opcodes.ASM9, nextClassVisitor) {

    override fun visitField(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        value: Any?
    ): FieldVisitor {
        return super.visitField(access, name, descriptor, signature, value)
    }

    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        val methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions)
        println("asm.ActivityClassVisitor visitMethod name:${name} descriptor:${descriptor} signature:${signature} exceptions: ${exceptions}")
        val newMethodVisitor =
            object : AdviceAdapter(Opcodes.ASM9, methodVisitor, access, name, descriptor) {
                @Override
                override fun onMethodEnter() {
                    // 方法开始
                    super.onMethodEnter()
                    println("asm.ActivityClassVisitor visitMethod name:${name} onMethodEnter")
                }

                @Override
                override fun onMethodExit(opcode: Int) {
                    // 方法结束
                    super.onMethodExit(opcode)

                    println("asm.ActivityClassVisitor visitMethod name:${name} onMethodExit opcode:${opcode}")
                }
            }
        return newMethodVisitor
    }

    override fun visitEnd() {
        super.visitEnd()
    }

    private fun isNeedVisiMethod(name: String?): Boolean {
        return true
    }
}