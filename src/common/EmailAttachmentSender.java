package common;
 
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
 
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
 
public class EmailAttachmentSender {
	 
	public static String[] attachFiles;
 
    public static void sendEmailWithAttachments(String host, String port, final String userName, final String password, String toAddress, String subject, String message, String[] attachFiles)throws AddressException, MessagingException 
    {
    	
    	attachFiles = new String[3];
        attachFiles[0] = "./result/result.html";
        attachFiles[1] = "./result/result.log";
        attachFiles[2] = "./test-output/emailable-report.html";
    	
        // sets SMTP server properties
    	 java.util.Properties props = null;
         props = System.getProperties();
       // Properties properties = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.socketFactory.port", port);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.user", userName);
        props.put("mail.password", password);
        Session session = Session.getInstance(props, new javax.mail.Authenticator()
        // creates a new session with an authenticator
        //Authenticator auth = new Authenticator() 
        {
            public PasswordAuthentication getPasswordAuthentication() 
            {
                return new PasswordAuthentication(userName, password);
            }
        });
        //session.setDebug(true);	
        
      try
      {
        //Session session = Session.getInstance(properties, auth);
 
        // creates a new e-mail message
        Message msg = new MimeMessage(session);
 
        msg.setFrom(new InternetAddress(userName));
        InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
 
        // creates message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(message, "text/html");
 
        // creates multi-part
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
 
        // adds attachments
        if (attachFiles != null && attachFiles.length > 0) 
        {
            for (String filePath : attachFiles) {
                MimeBodyPart attachPart = new MimeBodyPart();
 
                try {
                    attachPart.attachFile(filePath);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
 
                multipart.addBodyPart(attachPart);
            }
        }
 
        // sets the multi-part as e-mail's content
        msg.setContent(multipart);
 
        Transport transport = session.getTransport("smtp");
        transport.connect(host, userName, password);
        transport.sendMessage(msg, msg.getAllRecipients());
        transport.close();
        // sends the e-mail
        Transport.send(msg);
      }
      catch(Exception e)
      {
    	  throw new RuntimeException(e);
      }
    }
 
    /**
     * Test sending e-mail with attachments
     */
    public static void main(String[] args) {
        // SMTP info
        String host = "smtp.gmail.com";
        String port = "587";
        String mailFrom = "nextoryautomation130@gmail.com";
        String password = "nextory12345";
 
        // message info
        String mailTo = "vibhav89.v@gmail.com";
        String subject = "New email with attachments";
        String message = "I have some attachments for you.";
 
        // attachments
//        string[] attachfiles = new string[2];
//        attachfiles[0] = "./result/result.html";
//        attachFiles[1] = "e:/Test/Music.mp3";
//        attachFiles[2] = "e:/Test/Video.mp4";
// 
        try {
            sendEmailWithAttachments(host, port, mailFrom, password, mailTo, subject, message, attachFiles);
            System.out.println("Email sent.");
        } catch (Exception ex) {
            System.out.println("Could not send email.");
            ex.printStackTrace();
        }
    }
}