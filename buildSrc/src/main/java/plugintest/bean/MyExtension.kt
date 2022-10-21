package plugintest.bean

/**
 * @author liguandong
 * @data 2022/9/28
 *
 */
open class MyExtension {
    var name: String? = null
    var verCode = 1
    override fun toString(): String {
        return "MyExtension(name=$name, verCode=$verCode)"
    }

}