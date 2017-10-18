package context_dispatcher

import kotlinx.coroutines.experimental.*

/**
 * @author Minh
 * 10/15/2017
 */
fun main(args: Array<String>) = runBlocking<Unit> /* 1 */ {
    val jobs = arrayListOf<Job>()
    //not confined -- will work with main thread. Suitable for IO tasks which do not consume much CPU resources.
    jobs += launch(Unconfined) {
        println("      'Unconfined': I'm working in thread ${Thread.currentThread().name}")
    }
    //context of the parent, runBlocking coroutine
    jobs += launch(coroutineContext) {
        println("'coroutineContext': I'm working in thread ${Thread.currentThread().name}")
    }
    //will get dispatched to ForkJoinPool.commonPool (or equivalent). Sharable thread.
    jobs += launch(CommonPool) {
        println("      'CommonPool': I'm working in thread ${Thread.currentThread().name}")
    }
    //will get its own new thread. Private thread
    jobs += launch(newSingleThreadContext("MyOwnThread")) {
        println("          'newSTC': I'm working in thread ${Thread.currentThread().name}")
    }
    jobs.forEach { it.join() }

    //The coroutine context of 1
    println("My job is ${coroutineContext[Job]}")
}