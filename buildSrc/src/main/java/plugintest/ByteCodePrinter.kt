package plugintest

import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import java.io.File

/**
 * @author liguandong
 * @data 2022/10/10
 */
object ByteCodePrinter {
    @JvmStatic
    fun main(args: Array<String>) {
//        val classFileName = "ByteCodeTest"
        val readClassFilePath = "buildSrc/build/classes/java/main/ByteCodeTest.class"
        val writeClassFilePath = "buildSrc/src/main/java/ByteCodeTestModify.class"
        // 1 读取字节码
        val classReader = ClassReader(File(readClassFilePath).inputStream())

        val classWriter = ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
        classReader.accept(MyClassVisitor(classWriter), ClassReader.EXPAND_FRAMES or ClassReader.SKIP_FRAMES)
        File(writeClassFilePath).outputStream().write(classWriter.toByteArray())
    }

     class MyClassVisitor(classVisitor: ClassVisitor):ClassVisitor(Opcodes.ASM9,classVisitor){

    }
}