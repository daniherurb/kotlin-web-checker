# Welcome to my Kotlin Web Checker!
This project is a very simple Kotlin application. 
It allows users to check the status of their websites periodically and get an email
notification everytime one of their services is down.

## Configuration
In order to use this application, some previous configuration is needed. First, you 
need to modify the `config.json` file located in the root folder. 

This project uses Gmail's SMTP server to send email notifications. Therefore, you need to create an
application password for your Gmail account. You can do this [following this support article](https://support.google.com/accounts/answer/185833?hl=en). 
_You will need to activate 2-Step Verification in order to create an application password._

Fill each property as required:

- **urls**: An array of URLs you want to monitor. Please, use "https://" format.
- **sleep**: The time interval (in minutes) between each check.
- **sender**: The email address from which the notifications will be sent.
- **key**: The application-specific password for the sender email.
- **receiver**: The email address that will receive the notifications.

Here is an example of how the configuration file should look like:

```json
{
  "urls": ["https://daniherurb.site", "https://seveneat.com", "https://taylorswift.com"],
  "sleep": 2,
  "sender": "senderemail@gmail.com",
  "key": "abcd efgh ijkl mnop",
  "receiver": "receiveremail@gmail.com"
}
```

## Running the application

Before running the application, run the following command in your terminal to install required dependencies:

```bash
./gradlew run
```

From the desk of [daniherurb](https://daniherurb.site)