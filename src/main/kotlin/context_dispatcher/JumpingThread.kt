package context_dispatcher

import kotlinx.coroutines.experimental.newSingleThreadContext
import kotlinx.coroutines.experimental.run
import kotlinx.coroutines.experimental.runBlocking

/**
 * @author Minh
 * 10/17/2017
 */
fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

fun main(args: Array<String>) {
    val ctx1 = newSingleThreadContext("Ctx1")
    val ctx2 = newSingleThreadContext("Ctx2")
    runBlocking(ctx1) {
        log("Started in ctx1")
        run(ctx2) {
            log("Working in ctx2")
        }
        log("Back to ctx1")
    }
}