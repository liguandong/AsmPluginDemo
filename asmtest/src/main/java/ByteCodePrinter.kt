import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.util.Textifier
import org.objectweb.asm.util.TraceClassVisitor
import java.io.File
import java.io.PrintWriter

/**
 * @author liguandong
 * @data 2022/10/10
 */
object ByteCodePrinter {
    @JvmStatic
    fun main(args: Array<String>) {
        val readClassFilePath = "buildSrc/build/classes/java/main/ByteCodeTest.class"
        val writeClassFilePath = "buildSrc/src/main/java/ByteCodeTestPrinter.class"
        // 1 定义ClassReader，并且读取字节码
        val classReader = ClassReader(File(readClassFilePath).inputStream())
        //2 定义ClassWriter,用于获取字节码
        val classWriter = ClassWriter(ClassWriter.COMPUTE_MAXS)
        //3 定义中间人ClassVisitor，这里用系统TraceClassVisitor,并委托给classWriter
        val classVisitor = TraceClassVisitor(classWriter, Textifier(),PrintWriter(System.out, true))
        //4 开始事件调用
        classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES or ClassReader.SKIP_FRAMES)
        File(writeClassFilePath).outputStream().write(classWriter.toByteArray())
    }
}