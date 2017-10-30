package share_mutable_state

import kotlinx.coroutines.experimental.channels.ReceiveChannel
import kotlinx.coroutines.experimental.channels.produce
import kotlinx.coroutines.experimental.runBlocking

/**
 * @author Minh
 * 10/30/2017
 */

//pipeline pattern
fun main(args: Array<String>) = runBlocking<Unit> {
    val numbers = produceNumbers()
    val squares = square(numbers)
    for (i in 1..5) println(squares.receive())
    println("Finish")
    squares.cancel() //the app is finished but need to cancel if the app is continuously running
    numbers.cancel()
}

fun produceNumbers() = produce<Int> {
    var x = 1
    while (true) send(x++)
}

fun square(numbers: ReceiveChannel<Int>) = produce<Int> {
    for (x in numbers) send(x * x)
}