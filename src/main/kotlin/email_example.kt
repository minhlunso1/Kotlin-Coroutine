import kotlinx.coroutines.experimental.*

/**
 * @author Minh
 * 10/8/2017
 */
suspend fun sendEmail(r: String, msg: String): Boolean {
    delay(2000)
    println("Sent $msg to $r")
    return true
}

suspend fun getReceiverAddressFromDatabase(): String {
    delay(1000)
    return "aminh1603@gmail.com"
}

suspend fun sendEmailSuspending(): Boolean {
    val msg = async (CommonPool + CoroutineName("other "))  {
        delay(500)
        "The message content"
    }
    val recipient = async (CommonPool + CoroutineName("other ")) {
        getReceiverAddressFromDatabase()
    }
    println("Waiting for email data")
    val sendStatus = async (CommonPool + CoroutineName("other ")) {
        sendEmail(recipient.await(), msg.await())//must return two Deferred then execute sendMail
    }
    return sendStatus.await()
}

fun main(args: Array<String>) = runBlocking {
    val job = launch(CommonPool) {//CommonPool -> CoroutineContext - CoroutineDispatcher
        val result = sendEmailSuspending()
//        sendEmailSuspending()
        println("Email sent " + result)
    }
    job.join()
    println("Main finished")
}