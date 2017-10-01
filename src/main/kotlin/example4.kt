import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking

/**
 * @author Minh
 * 9/30/2017
 */

/*
** Coroutines ARE light-weight. If it was a thread, could get OutOfMemoryException
 */
fun main(args: Array<String>) = runBlocking {
    val jobs = List(100_000) {
        launch {
            delay(3000)
            println("Work")
        }
    }
    jobs.forEach{ it.join() }
    //The result is after 3s, it will print 100k Work
}