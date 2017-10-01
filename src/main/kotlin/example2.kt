import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking

/**
 * @author Minh
 * 9/24/2017
 */
fun main(args: Array<String>) = runBlocking {
//    val job = launch(CommonPool) { //launch returns a Job and does not carry any resulting value
//        val result = delayFirst()
//        println("$result")
//    }
    val job = launch { doSuspendFunction() }
    print("The result: ")
    job.join() //with join to wait for completion. Otherwise, there is no result value
}

suspend fun doSuspendFunction() {
    val result = delayFirst()
    println("$result")
}