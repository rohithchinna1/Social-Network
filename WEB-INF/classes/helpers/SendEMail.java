package helpers;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import beans.User;

public class SendEMail {
	Properties props = new Properties();
	Session session;

	public SendEMail() {
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(
								"projectsmallworld2015@gmail.com",
								"smallworld2015");
					}
				});
	}

	public void NewUser(User user,String Link) {
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(
					"projectsmallworld2015@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(user.getEmail()));
			message.setSubject("Registration - SmallWorld");
			message.setContent("Hello "+user.getFirstname()+" "+user.getLastname()+","
					+ "<br><br>You are successfully registered for SmallWorld"
					+ "<br><br><a href='"+Link+"'>Please click here to activate your account</a>"
					+ "<br><br>Regards,"
					+ "<br>SmallWorld Team","text/html; charset=utf-8");
			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void ChangePassword(User user) {
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(
					"projectsmallworld2015@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(user.getEmail()));
			message.setSubject("Forgot Password - SmallWorld");
			message.setContent("Hello "+user.getFirstname()+" "+user.getLastname()+","
					+ "<br><br><b><u>Your credentials for SmallWorld</u></b>"
					+ "<br><b>Email:</b> " + user.getEmail()
					+ "<br><b>New Password:</b> " + user.getPassword()
					+ "<br><br>You can change you password after login using change password page."
					+ "<br><br>Regards,"
					+ "<br>SmallWorld Team","text/html; charset=utf-8");
			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
