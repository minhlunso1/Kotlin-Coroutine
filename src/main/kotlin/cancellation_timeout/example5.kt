package cancellation_timeout

import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking

/**
 * @author Minh
 * 10/18/2017
 */

/* When the coroutine has context inheriting from parent, it become the child of parent coroutine.
** When the parent cancels, the child cancels too.
 */
fun main(args: Array<String>) = runBlocking {
    val parent = launch {
        //separated context
        val child1 = launch {
            println("1_1")
            delay(1000)
            println("1_2")
        }
        //inherits from parent context
        val child2 = launch(coroutineContext) {
            println("2_1")
            delay(1000)
            println("2_2")
        }
        child1.join()
        child2.join()
    }
    delay(500)
    parent.cancel()
    delay(1000)
    println("main end 1")

    val parent2 = launch {
        repeat(3) { i -> // launch a few children jobs
            launch(coroutineContext)  {
                delay((i + 1) * 200L) // variable delay 200ms, 400ms, 600ms
                println("Coroutine $i is done")
            }
        }
        println("parent2: Done, do not explicitly join my children that are still active")
    }
    parent2.join() // wait for completion of the request, including all its children
    println("main end 2")
}