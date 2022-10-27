package plugintest

import plugintest.bean.MyExtension
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

/**
 * @author liguandong
 * @data 2022/9/30
 *
 */
// https://docs.gradle.org/current/userguide/custom_tasks.html#sec:writing_a_simple_task_class
abstract class TestPluginTask1 @Inject constructor(private val myExtension: MyExtension):DefaultTask() {

    // https://docs.gradle.org/current/userguide/lazy_configuration.html#lazy_properties
    //Gradle 提供惰性属性，它会延迟属性值的计算，直到实际需要它。这些为构建脚本和插件作者提供了三个主要好处：
    @get:Input
    abstract val message: Property<String>

    @TaskAction
    fun doTask(){
        println("Hello Task ${myExtension.name} ${message.get()}")
    }
}