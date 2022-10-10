import bean.MyExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author liguandong
 * @data 2022/9/28
 *
 */
class TestPlugin3 : Plugin<Project> {
    override fun apply(project: Project) {
        println("TestPlugin3 apply ")
    }
}