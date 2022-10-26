package asm

import com.android.build.api.instrumentation.*
import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.util.TraceClassVisitor
import java.io.File
import java.io.PrintWriter

/**
 * @author liguandong
 * @data 2022/10/8
 *
 * 谷歌官方例子
 *
 */
class AsmTracePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        println("AmsPlugin apply ")
        val androidComponents = project.extensions.getByType(AndroidComponentsExtension::class.java)
        androidComponents.onVariants { variant ->
            println("onVariants ${variant.name}")
            // 注册工厂和设置输入参数
            variant.instrumentation.transformClassesWith(
                ExampleClassVisitorFactory::class.java,
                InstrumentationScope.PROJECT) {
                it.writeToStdout.set(true)
            }

            //https://developer.android.google.cn/reference/tools/gradle-api/7.0/com/android/build/api/instrumentation/FramesComputationMode.html
            /**
             *
             *
            COMPUTE_FRAMES_FOR_ALL_CLASSES
            读取原始类时将跳过堆栈帧和最大堆栈大小，并且不会由ClassWriter计算。
            (使用此模式将对构建速度产生很大影响，仅在绝对必要时使用。
            如果您的插桩过程需要重新计算帧数和/或最大值，则使用此模式，并且将有两个类 A 和 B，其中最低的公共超类在插桩之前和之后不会相同。)

            COMPUTE_FRAMES_FOR_INSTRUMENTED_CLASSES
            读取原始类时将跳过堆栈帧和最大堆栈大小，而是由ClassWriter根据原始类的类路径从头开始计算。
            (如果您的检测过程需要重新计算所有检测类的帧和/或最大值，并且对于任何两个类 A 和 B，检测前后最低的公共超类将相同，请使用此模式。

            COMPUTE_FRAMES_FOR_INSTRUMENTED_METHODS
            ClassWriter将根据原始类的类路径为任何修改或添加的方法计算堆栈帧和最大堆栈大小。
            (如果您的检测过程只需要为每个类中的修改方法重新计算帧和/或最大值，请使用此模式。)

            COPY_FRAMES
            堆栈帧和最大堆栈大小将从原始类复制到检测的类
             (如果您的检测过程不需要重新计算帧数或最大值，请使用此模式。)
             */
            variant.instrumentation.setAsmFramesComputationMode(FramesComputationMode.COPY_FRAMES)
        }
    }

    interface ExampleParams : InstrumentationParameters {
        @get:Input
        val writeToStdout: Property<Boolean>
    }

    abstract class ExampleClassVisitorFactory :
        AsmClassVisitorFactory<ExampleParams> {

        override fun createClassVisitor(
            classContext: ClassContext,
            nextClassVisitor: ClassVisitor
        ): ClassVisitor {
            return if (parameters.get().writeToStdout.get()) {
                TraceClassVisitor(nextClassVisitor, PrintWriter(System.out))
            } else {
                TraceClassVisitor(nextClassVisitor, PrintWriter(File("trace_out")))
            }
        }

        override fun isInstrumentable(classData: ClassData): Boolean {
            //类过滤器
            return classData.superClasses.contains("androidx.appcompat.app.AppCompatActivity")
        }
    }
}