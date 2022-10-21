package plugintest

import org.gradle.api.Plugin
import org.gradle.api.Project
import plugintest.bean.MyExtension

/**
 * @author liguandong
 * @data 2022/9/28
 *
 */
class TestPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        println("TestPlugin apply ")
        val ext = project.extensions.create("myExtension", MyExtension::class.java)
        println("TestPlugin apply 输入参数 ${ext}")
        project.afterEvaluate {
            println("TestPlugin MyPlugin afterEvaluate  输入参数${ext}")
        }
        project.task("hello"){
            println("TestPlugin MyPlugin   输入参数${ext}")
            it.doFirst {
                println("TestPlugin MyPlugin afterEvaluate  输入参数${ext}")
                ext.name
            }
            it.doLast {
                println("TestPlugin MyPlugin doLast  输入参数${ext}")
            }
        }
        project.tasks.create("hello2", TestPluginTask1::class.java,ext).also {
            it.message.set("Property message")
        }
    }
}