package share_mutable_state

/**
 * @author Minh
 * 11/4/2017
 */
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.channels.*
import kotlin.coroutines.experimental.CoroutineContext

fun numbersFrom(context: CoroutineContext, start: Int) = produce<Int>(context) {
    var x = start
    while (true) { // infinite stream of integers from start
        println("numbersFrom: " + x)
        send(x++)
    }
}

fun filter(context: CoroutineContext, numbers: ReceiveChannel<Int>, prime: Int) = produce<Int>(context) {
    for (x in numbers) {
        println("filter: " + x + ", prime: " + prime)
        if (x % prime != 0)
            send(x)
    }
}

/*  The benefit of a pipeline that uses channels as shown below is that it can actually use multiple CPU cores
    if run it in CommonPool context.
*/
fun main(args: Array<String>) = runBlocking<Unit> {
    var cur = numbersFrom(coroutineContext, 2)
    for (i in 1..10) {
        val prime = cur.receive()
        println(prime)
        cur = filter(coroutineContext, cur, prime)
    }
    coroutineContext.cancelChildren() // cancel all children to let main finish
}