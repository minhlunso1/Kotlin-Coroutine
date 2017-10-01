import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch

/**
 * @author Minh
 * 9/26/2017
 */
fun main(args: Array<String>) {
    launch(CommonPool) { // create new coroutine in common thread pool
        delay(1000L)
        println("work in coroutine")
    }
    println("non-blocking thread, main function continues")
    Thread.sleep(2000L) // block main thread for 2 seconds to keep JVM alive. Similarly to using join to keep JVM alive.
}