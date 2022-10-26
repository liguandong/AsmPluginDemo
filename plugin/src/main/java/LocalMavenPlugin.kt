import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author liguandong
 * @data 2022/10/8
 *
 */
class LocalMavenPlugin:Plugin<Project> {
    override fun apply(project: Project) {
        println("LocalMavenPlugin apply")
    }
}