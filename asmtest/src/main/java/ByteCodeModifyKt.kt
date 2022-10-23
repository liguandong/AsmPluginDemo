import adapter.PrintAdapter
import adapter.TimeCostAdapter
import org.objectweb.asm.*
import org.objectweb.asm.commons.AdviceAdapter
import org.objectweb.asm.commons.Method
import java.io.File

/**
 * @author liguandong
 * @data 2022/10/10
 *
 *
 */
//方法描述符   https://www.yuque.com/mikaelzero/asm/mh871e
//类型描述符   https://www.yuque.com/mikaelzero/asm/vntppt
//字节码指令  https://www.yuque.com/mikaelzero/asm/ka0wu4
object ByteCodeModifyKt {
    @JvmStatic
    fun main(args: Array<String>) {
//        val readClassFilePath = "asmtest/out/production/classes/ByteCodeTestKt.class"
//        val writeClassFilePath = "asmtest/out/production/ByteCodeTestKt.class"
        val readClassFilePath = ReadClassFilePath
        val writeClassFilePath = WriteClassFilePath

        // 1 定义ClassReader，并且读取字节码
        val classReader = ClassReader(File(readClassFilePath).inputStream())
        //
        //2 定义ClassWriter,用于获取字节码,
        val classWriter = ClassWriter(ClassWriter.COMPUTE_MAXS)
        //3 定义中间人ClassVisitor，并委托给classWriter
        val classVisitor = MyClassVisitor(classWriter)
        //4 开始事件调用
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
                    var slot = 0

                    @Override
                    override fun onMethodEnter() {
                        super.onMethodEnter()
                        // 方法开始
                        println("aAdviceAdapter  onMethodEnter  name:${name} ")
                        visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
                        visitVarInsn(LSTORE, 1);
                    }

                    @Override
                    override fun onMethodExit(opcode: Int) {
                        super.onMethodExit(opcode)
                        // 方法结束
                        println("AdviceAdapter onMethodExit name:${name} opcode:${opcode}")

                        visitTypeInsn(NEW, "java/lang/StringBuilder");
                        visitInsn(DUP);
                        visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
                        visitLdcInsn("cost: ");
                        visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
                        visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
                        visitVarInsn(LLOAD, 1);
                        visitInsn(LSUB);
                        visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false);
                        visitLdcInsn("ms");
                        visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
                        visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
                        visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                        visitInsn(SWAP);
                        visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/Object;)V", false);

                    }
                }

            return newMethodVisitor
//            val printAdapter = PrintAdapter(methodVisitor,access,name,descriptor)
//            val timeCostAdapter = TimeCostAdapter(printAdapter,access,name,descriptor)
//            return timeCostAdapter

//            val timeCostAdapter = TimeCostAdapter(methodVisitor,access,name,descriptor)
//            val printAdapter = PrintAdapter(timeCostAdapter,access,name,descriptor)
//            return printAdapter
        }
    }

    class MyPrintAdapter(
        methodVisitor: MethodVisitor, access: Int,
        name: String?,
        descriptor: String?
    ) : AdviceAdapter(ASM9, methodVisitor, access, name, descriptor) {

        override fun onMethodEnter() {
            super.onMethodEnter()
        }

        override fun onMethodExit(opcode: Int) {
            super.onMethodExit(opcode)
        }
    }

    class MyTimeCostAdapter(
        methodVisitor: MethodVisitor, access: Int,
        name: String?,
        descriptor: String?
    ) : AdviceAdapter(ASM9, methodVisitor, access, name, descriptor) {

        override fun onMethodEnter() {
            super.onMethodEnter()
        }

        override fun onMethodExit(opcode: Int) {
            super.onMethodExit(opcode)
        }
    }

}