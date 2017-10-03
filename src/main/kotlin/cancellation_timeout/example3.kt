package cancellation_timeout

import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.runBlocking
import kotlinx.coroutines.experimental.withTimeout

/**
 * @author Minh
 * 10/3/2017
 */
fun main(args: Array<String>) = runBlocking<Unit> {
    withTimeout(1300L) {
        repeat(1000) { i ->
            println(i)
            delay(500L)
        }
    }
    //Will throw TimeoutCancellationException - the subclass of CancellationException
}