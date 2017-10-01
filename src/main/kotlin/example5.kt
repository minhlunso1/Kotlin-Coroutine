import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking

/**
 * @author Minh
 * 9/30/2017
 */

/*
** Coroutine acts as a daemon thread
 */
fun main(args: Array<String>) = runBlocking {
    launch(CommonPool) {
        repeat(1000) { i ->
            println(i)
            delay(1000L)
        }
    }
    delay(1200L)//After 1.2s end process. Do not keep process alive -> similar to daemon thread
}