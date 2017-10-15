import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.runBlocking
import kotlin.system.measureTimeMillis

/**
 * @author Minh
 * 10/15/2017
 */

// The result type of async is Deferred.
fun asyncFirst() = async {
    //Async-style function does not allow return (only Deferred).
//    delay(3000)
//    return 1
    delayFirst()
}

fun asyncSecond() = async {
    delaySecond()
}

fun main(args: Array<String>) {
    val time = measureTimeMillis {
        val first = asyncFirst()
        val second = asyncSecond()
        //async can be used outside Coroutine. However, Coroutine is required when it come to getting result.
        runBlocking {
            println("The sum of ${first.await() + second.await()}")
        }
    }
    println("Total time: " + time)
}