package com.timesheet.login;

import java.util.Date;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.event.ConnectionEvent;
import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;
import javax.mail.event.ConnectionListener;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailService implements TransportListener,ConnectionListener {

 private String smtpServerAddress = "smtp.gmail.com";
    private EmailBean emailBean = null;

    public EmailService() {
        super();
    }

    public void sendEmail(EmailBean bean) {
        //System.out.println("Inside sendEmail method of " + this.getClass().getName());
        try {
            emailBean = bean;

            String emailSubject = emailBean.getEmailSubject();
            String emailMessage = emailBean.getEmailMesaage();
            Date emailDate = emailBean.getEmailDate();
            String fromAdd = emailBean.getFromAddress();
            String toAdd = emailBean.getToAddress();
            boolean isHTMLContents = emailBean.isHTMLContents();

            if (emailDate == null) {
                emailDate = new Date();
            }

            // set the SMTP host property value
            Properties properties = System.getProperties();
            properties.put("mail.smtp.host", smtpServerAddress);
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.auth", "true");

            // create a JavaMail session
            Session session = Session.getInstance(properties, new MyAuthenticator());

            // create a new MIME message
            MimeMessage message = new MimeMessage(session);

            //set the sent date of the message
            message.setSentDate(emailDate);

            // set the subject
            message.setSubject(emailSubject);

            // set the message body
            if (isHTMLContents) {
                message.setContent(emailMessage, "text/html");
            } else {
                message.setContent(emailMessage, "text/plain");
            }

            // set the from address
            Address fromAddress = new InternetAddress(fromAdd);
            //System.out.println("The Address is "+fromAddress);
            message.setFrom(fromAddress);

            Address[] toAddress = null;

            // set the to address
            if (toAdd != null) {
                toAddress = InternetAddress.parse(toAdd);
                //System.out.println("The ToAddress is=="+toAddress);
                message.setRecipients(Message.RecipientType.TO, toAddress);
            } else {
                throw new MessagingException("No \"To\" address specified");
            }

            // send the message
            Transport trans = null;
            try {
                trans = session.getTransport("smtp");
                session.setDebug(true);
                trans.connect();
                trans.addConnectionListener(this);
                trans.addTransportListener(this);
                //System.out.println("Before File Attach.....");
                message.setDescription("");
                trans.sendMessage(message, toAddress);
                Thread.sleep(100);
                emailBean.setSent(true);
            }//end of try statement
            catch (InterruptedException e) {
                emailBean.setSent(false);
                //System.out.println("Error while waiting for mail send status");
                e.printStackTrace();
            }//end of catch statement
            finally {
                if (trans != null) {
                    try {
                        trans.close();
                    } catch (Exception e) {
                        emailBean.setSent(false);
                        //System.out.println("Error while closing mail trasaction. " + e);
                        e.printStackTrace();
                    }
                }
            }//end of finally statement
        }//end of try statement
        catch (AddressException e) {
            emailBean.setSent(false);
            e.printStackTrace();
            //System.out.println("Invalid e-mail address." + e.getMessage());
        }//end of catch statement
        catch (SendFailedException e) {
            emailBean.setSent(false);
            e.printStackTrace();
            //System.out.println("Send failed." + e.getMessage());
        }//end of catch statement
        catch (MessagingException e) {
            emailBean.setSent(false);
            e.printStackTrace();
            //System.out.println("Unexpected error." + e.getMessage());
        }//end of catch statement
        catch (Exception ex) {
            emailBean.setSent(false);
            ex.printStackTrace();
        }

    }//end of method - SendEmail

   public void messageDelivered(TransportEvent arg0) {
        emailBean.setSent(true);
        //System.out.println("Email is delievered.");
    }

    public void messageNotDelivered(TransportEvent arg0) {
        emailBean.setSent(false);
        //System.out.println("Email is not delievered.");
    }

    public void messagePartiallyDelivered(TransportEvent arg0) {
        emailBean.setSent(false);
        //System.out.println("Email is partially delievered.");
    }

    public void closed(ConnectionEvent arg0) {
        //System.out.println("Connection to smtp server is closed.");
    }

    public void disconnected(ConnectionEvent arg0) {
        //System.out.println("Connection to smtp server is disconnected.");
    }

    public void opened(ConnectionEvent arg0) {
        //System.out.println("Connection to smtp server is opened.");
    }

}//end of class - EmailComponent

class MyAuthenticator extends Authenticator {

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication("ankioza@gmail.com", "iamthebest");
    }
}

