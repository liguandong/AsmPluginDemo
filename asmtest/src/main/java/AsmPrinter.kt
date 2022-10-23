import org.objectweb.asm.ClassReader
import org.objectweb.asm.util.ASMifier
import org.objectweb.asm.util.Textifier
import org.objectweb.asm.util.TraceClassVisitor
import java.io.File
import java.io.IOException
import java.io.PrintWriter

/**
 * @author liguandong
 * @data 2022/10/10
 */
object AsmPrinter {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        // (1) 设置参数
        val className = ReadClassFilePath
        val asmCode = false
        // (2) 打印结果
        val printer = if (asmCode) ASMifier() else Textifier()
        val printWriter = PrintWriter(System.out, true)
        val traceClassVisitor = TraceClassVisitor(null, printer, printWriter)
        ClassReader(File(className).inputStream()).accept(traceClassVisitor, ClassReader.SKIP_FRAMES or ClassReader.SKIP_DEBUG)
    }
}