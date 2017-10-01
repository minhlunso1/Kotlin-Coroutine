package cancellation_timeout

import kotlinx.coroutines.experimental.*

/**
 * @author Minh
 * 10/1/2017
 */
fun main(args: Array<String>) = runBlocking {
    val job = launch(CommonPool) {
        var i = 0
        var nextPrintTime = System.currentTimeMillis()
//        while (i < 100) { // computation loop, just wastes CPU
//            if (System.currentTimeMillis() >= nextPrintTime) {
//                println("Value: ${i++}")
//                nextPrintTime += 500L
//            }
//        }
        //Cancel computation loop
        while (isActive) {
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("Value: ${i++}")
                nextPrintTime += 500L
            }
        }
    }
    delay(1200L)
    job.cancelAndJoin()
    println("main end")
}