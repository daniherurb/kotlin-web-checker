import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import javax.mail.Message
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


class Checker(private val link: String) {

    fun checkStatus() {
        val uri = URI(this.link)
        val httpClient : HttpClient = HttpClient.newHttpClient()
        val request : HttpRequest = HttpRequest.newBuilder().uri(uri).build()
        try {
            val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
            if (response.statusCode() == 200) {
                println("Check on $link done: successful!")
            }
        } catch (e: Exception) {
            println("ERROR! Unsuccessful connection to $this.link")
            sendEmail(this.link)
        }
    }

    private fun sendEmail(link : String) {
        val configurationParameters : ConfigurationParameters = ConfigurationParameters.getConfiguration()
        val properties = System.getProperties()
        properties["mail.smtp.host"] = "smtp.gmail.com"
        properties["mail.smtp.user"] = configurationParameters.sender
        properties["mail.smtp.clave"] = configurationParameters.key
        properties["mail.smtp.auth"] = "true"
        properties["mail.smtp.starttls.enable"] = "true"
        properties["mail.smtp.port"] = "587"

        val session : Session = Session.getDefaultInstance(properties)
        val mimeMessage = MimeMessage(session)

        try {
            mimeMessage.setFrom(InternetAddress(configurationParameters.sender))
            mimeMessage.addRecipient(
                Message.RecipientType.TO,
                InternetAddress(configurationParameters.receiver)
            )
            val subject = "Service at $link is unavailable"
            mimeMessage.subject = subject
            val body = "We regret to inform you that your service at $link is unavailable. \n" +
                    "Good luck fixing it!"
            mimeMessage.setText(body)
            val transport: Transport = session.getTransport("smtp")
            transport.connect("smtp.gmail.com", configurationParameters.sender, configurationParameters.key)
            transport.sendMessage(mimeMessage, mimeMessage.allRecipients)
            transport.close()
        }
        catch (e: Exception) {
            throw Exception("An error happened while sending the email: ${e.message}")
        }
    }

    override fun toString(): String {
        return this.link
    }
}