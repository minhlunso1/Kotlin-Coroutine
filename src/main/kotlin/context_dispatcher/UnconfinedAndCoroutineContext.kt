package context_dispatcher

import kotlinx.coroutines.experimental.*

/**
 * @author Minh
 * 10/17/2017
 */
fun main(args: Array<String>) = runBlocking<Unit> {
    val jobs = arrayListOf<Job>()
    /*
        Unconfined dispatcher is appropriate when coroutine does not consume CPU time nor updates any shared data (like UI)
        that is confined to a specific thread.
     */
    jobs += launch(Unconfined) { // not confined -- will work with main thread
        println("      'Unconfined': I'm working in thread ${Thread.currentThread().name}")
        delay(500)
        println("      'Unconfined': After delay in thread ${Thread.currentThread().name}")
    }
    /*
        coroutineContext property that is available inside the block of any coroutine via CoroutineScope interface,
        is a reference to a context of this particular coroutine. This way, a parent context can be inherited.
        The default dispatcher for runBlocking coroutine, in particular, is confined to the invoker thread,
        so inheriting it has the effect of confining execution to this thread with a predictable FIFO scheduling.
     */
    jobs += launch(coroutineContext) { // context of the parent, runBlocking coroutine
        println("'coroutineContext': I'm working in thread ${Thread.currentThread().name}")
        delay(1000)
        println("'coroutineContext': After delay in thread ${Thread.currentThread().name}")
    }
    jobs += launch(coroutineContext) { // context of the parent, runBlocking coroutine
        println("${Thread.currentThread().name}")
    }
    jobs.forEach { it.join() }
}