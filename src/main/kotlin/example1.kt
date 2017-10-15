import kotlinx.coroutines.experimental.*
import kotlin.system.measureTimeMillis

/**
 * @author Minh
 * 9/24/2017
 */
suspend fun delayFirst(): Int {
    delay(3000L) //can only used in coroutine
    println("delay the first done")
    return 1
}

//the same as delayFirst()
fun delayFirstWithRunBlocking(): Int = runBlocking<Int> {
    delay(3000L)
    println("delay the first done")
    return@runBlocking 1
}


suspend fun delaySecond(): Int {
    delay(1000L)
    println("delay the second done")
    return 2
}

fun main(args: Array<String>) = runBlocking {
    val time = measureTimeMillis {
//        val first = delayFirst()
//        val second = delaySecond()
//        println("The sum is ${first + second}")

        /*
            Time  faster due to co-execution
         */
        val first = async { delayFirst() }
        val second = async { delaySecond() }
        //Lazily started async
        val third = async (start = CoroutineStart.LAZY) { delayFirst() }
        val fourth = async (start = CoroutineStart.LAZY) { delaySecond() }
        println("The sum is ${first.await() + second.await()}")

        /*
            async returns a Deferred - a light-weight non-blocking future that represents a promise to provide a result later.
            Can use .await() on a deferred value to get its eventual result, but Deferred is also a Job -> can cancel it if needed.
         */
//        val first = async(CommonPool) { delayFirst() }
//        val second = async(CommonPool) { delaySecond() }
//        println("The sum without 'second' is ${first.await()}")

        /*
            CoroutineStart.LAZY: tarts coroutine only when its result is needed by some await or if a start function is invoked.
         */
//        val first = async(CommonPool, CoroutineStart.LAZY) { delayFirst() }
//        val second = async(CommonPool, CoroutineStart.LAZY) { delaySecond() }
//        println("The sum without 'second' is ${first.await()}")
    }
    println("Time in ms: $time")
}