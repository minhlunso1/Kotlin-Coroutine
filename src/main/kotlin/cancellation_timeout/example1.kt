package cancellation_timeout

import kotlinx.coroutines.experimental.*

/**
 * @author Minh
 * 9/30/2017
 */
fun main(args: Array<String>) = runBlocking {
    val job = launch(CommonPool) {
        repeat(1000) { i ->
            println(i)
            delay(100L)
        }
    }
    delay(1200L)
    job.cancelAndJoin()
    println("main end")
}