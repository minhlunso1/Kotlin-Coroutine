package cancellation_timeout

import kotlinx.coroutines.experimental.*

/**
 * @author Minh
 * 10/3/2017
 */
fun main(args: Array<String>) = runBlocking<Unit> {
//    try {
//        withTimeout(1300L) {
//            repeat(1000) { i ->
//                println(i)
//                delay(500L)
//            }
//        }
//    } catch (e: TimeoutCancellationException) {
//        println("Catch exception")
//    }

    //withTimeoutOrNull is similar to withTimeout, but returns null on timeout instead of throwing an exception.
    withTimeoutOrNull(1300L) {
        repeat(1000) { i ->
            println(i)
            delay(500L)
        }
    }
}