package cancellation_timeout

import kotlinx.coroutines.experimental.*

/**
 * @author Minh
 * 9/30/2017
 */
fun main(args: Array<String>) = runBlocking {
    val job = launch(CommonPool) {
        try {
            repeat(1000) { i ->
                println(i)
                delay(100L)
            }
        } finally {
//            println("The usage is similar to Java. For example, execution after CancellationException")
            run(NonCancellable) {//If need to execute suspended funtion
                delay(1000L)
                println("non-cancellable suspsension")
            }
            println("non-cancellable")
        }
    }
    delay(1200L)
    job.cancelAndJoin()
    println("main end")
}