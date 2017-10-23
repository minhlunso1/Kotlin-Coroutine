package cancellation_timeout

import kotlinx.coroutines.experimental.*

/**
 * @author Minh
 * 10/23/2017
 */
fun main(args: Array<String>) = runBlocking {
    val job = Job() //create a job object to manage our lifecycle
    val coroutines = List(10) { i ->
        //they are all children of our job object
        launch(coroutineContext + job) {//use the context of main runBlocking thread, but with our own job object
            delay((i + 1) * 200L)
            println("Coroutine $i is done")
        }
    }
    println("Launched ${coroutines.size} coroutines")
    delay(1000L)// delay for half a second
//    job.join()
    job.cancelAndJoin()//cancel all our coroutines and wait for all of them to complete
    println("Cancelling the job!")
}