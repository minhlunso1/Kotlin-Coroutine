package context_dispatcher

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking

/**
 * @author Minh
 * 10/21/2017
 */
fun main(args: Array<String>) = runBlocking {
    val request = launch(coroutineContext) { // use the context of `runBlocking`
        println(Thread.currentThread().name)
        // spawns CPU-intensive child job in CommonPool !!!
        val job = launch(coroutineContext + CommonPool) {
            println(Thread.currentThread().name)
            println("job: I am a child of the request coroutine, but with a different dispatcher")
            delay(1000)
            println("job: I will not execute this line if my parent request is cancelled")
        }
        job.join()
    }
    delay(500)
    request.cancel()
    delay(1000)
}