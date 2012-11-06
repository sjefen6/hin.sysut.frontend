package hikst.frontend.server;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.http.auth.UsernamePasswordCredentials;

import hikst.frontend.client.MyService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.ibm.icu.impl.ICUBinary.Authenticate;


public class MyServiceImpl extends RemoteServiceServlet implements MyService
{
	private static final String SMTP_HOST_NAME = "smtp.gmail.com";
	private static final String SMTP_AUTH_USER = "eurokro@gmail.com";
	private static String SMTP_AUTH_PWD = "";
	
	public String mailFrom, mailFromPassword, usernameTo  ;
	

    public MyServiceImpl() {
		
	}
    /*
    public String getUser(){
    	return this.username;
    }
    
    public String getPass(){
    	return this.password;
    }*/

	@Override
	public boolean sendMail(final String mailFrom, final String mailFromPass , String mailTo, String mailSub,
			String mailMes) {
		//this.username = mailFrom;
		//this.password = mailFromPass;
		System.out.println(mailFromPass);
		
		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", SMTP_HOST_NAME);
		props.put("mail.smtp.user", mailFrom);
	    props.put("mail.smtp.password", mailFromPass);
	    props.put("mail.smtp.port", "587");
	    props.put("mail.smtp.auth", "true");
		//Authenticator auth = new SMTPAuthenticator();
	    Session session = Session.getDefaultInstance(props, new Authenticator() {
	    	protected PasswordAuthentication getPasswordAuthentication(){
	    		return new PasswordAuthentication(mailFrom, mailFromPass);
	    	}
		});
	    
	    boolean b = false;
	    try {
	      Message msg = new MimeMessage(session);
	      
	      msg.setFrom(new InternetAddress(mailFrom));
	      msg.addRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
	      msg.setSubject(mailSub);
	      //msg.setText(msgBody);
	      msg.setContent(mailMes, "text/html");
	      Transport.send(msg);
	      b = true;
	    } catch (AddressException e) {
	    	e.printStackTrace();
	    } catch (MessagingException e) {
	    	e.printStackTrace();
	    }
	    return b;
		
	}
	/*
	private class SMTPAuthenticator extends javax.mail.Authenticator
	{
		MyServiceImpl impl;
		private String password;
		private String username;
		public PasswordAuthentication getPasswordAuthentication()
	    {
	        String username = impl.getUser();
	        String password = impl.getPass();
	        return new PasswordAuthentication(username, password);
	    }
	}*/

	
}
