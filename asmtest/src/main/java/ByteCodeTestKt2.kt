/**
 * @author liguandong
 * @data 2022/10/10
 */
object ByteCodeTestKt2 {
    @JvmStatic
    fun main(args: Array<String>) {
        println("insert")
        println("Hello World!")
    }

    fun cost() {
        val start = System.currentTimeMillis()
        Thread.sleep(100)
        println("cost: ${System.currentTimeMillis() - start}ms")
    }
}