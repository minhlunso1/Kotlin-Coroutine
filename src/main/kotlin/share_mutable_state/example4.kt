package share_mutable_state

import kotlinx.coroutines.experimental.channels.*
import kotlinx.coroutines.experimental.runBlocking

/**
 * @author Minh
 * 10/24/2017
 */

/*
** 'produce' is coroutine that results returned from functions.
 */
fun produceSquares() = produce<Int> {
    for (x in 1..5) send(x * x)
}

fun main(args: Array<String>) = runBlocking<Unit> {
    val squares = produceSquares()
    squares.consumeEach { println(it) }
    println("Finish")
}