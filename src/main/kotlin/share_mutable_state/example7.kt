package share_mutable_state

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.CompletableDeferred
import kotlinx.coroutines.experimental.channels.actor
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import kotlin.coroutines.experimental.CoroutineContext
import kotlin.system.measureTimeMillis

/**
 * @author Minh
 * 11/16/2017
 */

suspend fun repeatRun(context: CoroutineContext, action: suspend () -> Unit) {
    val n = 1000 // number of coroutines to launch
    val k = 1000 // times an action is repeated by each coroutine
    val time = measureTimeMillis {
        val jobs = List(n) {
            launch(context) {
                repeat(k) { action() }//do nothing
            }
        }
        jobs.forEach { it.join() }
    }
    println("Completed ${n * k} actions in $time ms")
}

//fun main(args: Array<String>) = runBlocking<Unit> {
//    var counter = 0
//    repeatRun(CommonPool) {
//        counter++
//    }
//    println("Counter = $counter")//the result is not correct
//}

// Message types for messageCounterActor
sealed class CounterMessage
object IncCounter : CounterMessage() // one-way message to increment counter
class GetCounter(val response: CompletableDeferred<Int>) : CounterMessage() // a request with reply

// This function launches a new counter actor
fun messageCounterActor() = actor<CounterMessage> {
    var counter = 0 // actor state
    for (msg in channel) { // iterate over incoming messages
        when (msg) {
            is IncCounter -> counter++
            is GetCounter -> msg.response.complete(counter)
        }
    }
}

fun main(args: Array<String>) = runBlocking<Unit> {
    val counter = messageCounterActor()
    repeatRun(CommonPool) {
        counter.send(IncCounter)
    }
    // send a message to get a counter value from an actor
    val response = CompletableDeferred<Int>()
    counter.send(GetCounter(response))
    println("Counter = ${response.await()}")//the result is correct
    counter.close() // shutdown the actor
}