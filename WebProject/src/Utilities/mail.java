package Utilities;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class mail {
	
	public static void sendMail (String text,String to_email,String Topic) throws AddressException, MessagingException
	{
		String from_account = "omar123web1@gmail.com";
        String pass = "123@123web";
        Properties new_prop=new Properties();
        new_prop.setProperty("mail.smtp.host", "smtp.gmail.com");
        new_prop.setProperty("mail.smtp.port", "587");
        new_prop.setProperty("mail.smtp.auth", "true");
        new_prop.setProperty("mail.smtp.starttls.enable", "true");
        new_prop.put("mail.smtp.socketFactory.port", "587");
        new_prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
          Session session1 = Session.getInstance(new_prop, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from_account, pass);
                    }
                });
         Message Send_Mail = new MimeMessage(session1);
         Send_Mail.setFrom(new InternetAddress(from_account));
         Send_Mail.setRecipient(Message.RecipientType.TO, new InternetAddress(to_email));
         Send_Mail.setSubject(Topic);
         Send_Mail.setText(text);
         Transport.send(Send_Mail); //send the E-mail
	}

}
