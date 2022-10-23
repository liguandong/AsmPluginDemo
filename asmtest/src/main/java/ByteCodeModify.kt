import org.objectweb.asm.*
import org.objectweb.asm.commons.AdviceAdapter
import org.objectweb.asm.commons.Method
import java.io.File

/**
 * @author liguandong
 * @data 2022/10/10
 *
 */
//方法描述符   https://www.yuque.com/mikaelzero/asm/mh871e
//类型描述符   https://www.yuque.com/mikaelzero/asm/vntppt
//字节码指令  https://www.yuque.com/mikaelzero/asm/ka0wu4
object ByteCodeModify {
    @JvmStatic
    fun main(args: Array<String>) {
        val readClassFilePath = ReadClassFilePath
//        val writeClassFilePath = "asmtest/out/production/ByteCodeTest.class"
        val writeClassFilePath = WriteClassFilePath
        // 1 定义ClassReader，并且读取字节码
        val classReader = ClassReader(File(readClassFilePath).inputStream())
        //2 定义ClassWriter,用于获取字节码，自动计算栈帧和局部变量表的大小
        val classWriter = ClassWriter(ClassWriter.COMPUTE_MAXS)
        //3 定义中间人ClassVisitor，并委托给classWriter
        val classVisitor = MyClassVisitor(classWriter)
        //4 开始执行
        classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES)
        File(writeClassFilePath).outputStream().write(classWriter.toByteArray())
    }

    class MyClassVisitor(classVisitor: ClassVisitor) : ClassVisitor(Opcodes.ASM9, classVisitor) {

        override fun visitEnd() {
            super.visitEnd()
        }

        override fun visitMethod(
            access: Int,
            name: String?,
            descriptor: String?,
            signature: String?,
            exceptions: Array<out String>?
        ): MethodVisitor {
            println("$name $descriptor")
            val methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions)
            val newMethodVisitor =
                object : AdviceAdapter(Opcodes.ASM9, methodVisitor, access, name, descriptor) {
                    @Override
                    override fun onMethodEnter() {
                        // 方法开始
                        super.onMethodEnter()
                        println("aAdviceAdapter  onMethodEnter  name:${name} ")
                        getStatic(Type.getType("Ljava/lang/System;"),"out",Type.getType("Ljava/io/PrintStream;"))
//                        swap()
                        visitLdcInsn("insert")
                        invokeVirtual(Type.getType("Ljava/io/PrintStream;"),
                            Method("println","(Ljava/lang/Object;)V")
                        )
                    }

                    @Override
                    override fun onMethodExit(opcode: Int) {
                        // 方法结束
                        super.onMethodExit(opcode)
                        println("AdviceAdapter onMethodExit name:${name} opcode:${opcode}")
                    }
                }
            return newMethodVisitor
        }

        class MethodAdapter(
            methodVisitor: MethodVisitor,
            access: Int,
            name: String?,
            descriptor: String?
        ) : AdviceAdapter(Opcodes.ASM9, methodVisitor, access, name, descriptor) {

            override fun onMethodEnter() {
                super.onMethodEnter()

            }

            override fun onMethodExit(opcode: Int) {
                super.onMethodExit(opcode)
            }
        }
    }

}