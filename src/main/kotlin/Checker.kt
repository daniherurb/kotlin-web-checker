import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse


class Checker(private val link: String) {

    private val httpClient : HttpClient = HttpClient.newHttpClient()
    private var lastStatus : Status = Status.SUCCESS

    enum class Status(val value: Int) {
        SUCCESS(0),
        FAILURE(1)
    }

    fun checkStatus() {
        val uri = URI(this.link)
        val request : HttpRequest = HttpRequest.newBuilder().uri(uri).build()
        try {
            val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
            if (response.statusCode() == 200) {
                println("Check on $link done: successful!")
                if (this.lastStatus != Status.SUCCESS) {
                    EmailSender.sendEmailUp(this.link)
                }
                this.lastStatus = Status.SUCCESS
            }
        } catch (e: Exception) {
            println("ERROR! Unsuccessful connection to $link")
            if (this.lastStatus != Status.FAILURE) {
                println("Sending email notification...")
                EmailSender.sendEmailDown(this.link)
            }
            this.lastStatus = Status.FAILURE
        }
    }

    override fun toString(): String {
        return this.link
    }
}