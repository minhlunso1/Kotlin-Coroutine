package cancellation_timeout

import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking

/**
 * @author Minh
 * 10/18/2017
 */

/* When the coroutine has context inheriting from parent, it become the child of parent coroutine.
** When the parent cancels, the child cancels too.
 */
fun main(args: Array<String>) = runBlocking {
    val parent = launch {
        //separated context
        val child1 = launch {
            println("1_1")
            delay(1000)
            println("1_2")
        }
        //inherits from parent context
        val child2 = launch(coroutineContext) {
            println("2_1")
            delay(1000)
            println("2_2")
        }
        child1.join()
        child2.join()
    }
    delay(500)
    parent.cancel()
    delay(1000)
    println("main end")
}