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

abstract class TestPluginTask1 @Inject constructor(private val myExtension: MyExtension):DefaultTask() {

    @get:Input
    abstract val message: Property<String>

    @TaskAction
    fun doTask(){
        println("plugintest.MyTask ${myExtension.name} ${message.get()}")
    }
}