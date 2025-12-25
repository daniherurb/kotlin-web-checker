import javax.mail.Message
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

object EmailSender {

    private fun sendEmail(subject: String, body: String) {
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
            mimeMessage.subject = subject
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

    fun sendEmailUp(link: String) {
        val subject = "Service at $link is now up"
        val body = "Your service at $link is now available. \n" +
                "Good Job!"
        sendEmail(subject, body)
    }

    fun sendEmailDown(link: String) {
        val subject = "Service at $link is down"
        val body = "We regret to inform you that your service at $link is unavailable. \n" +
                "Good luck fixing it!"
        sendEmail(subject, body)
    }
}