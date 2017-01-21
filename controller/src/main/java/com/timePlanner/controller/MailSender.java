package com.timePlanner.controller;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


public class MailSender {
    private static final Logger LOGGER = LogManager.getLogger(MailSender.class);

    private final String username = "TimePlannerMail@gmail.com";
    private final String password = "17rub6k7z";

    private String recipientEmail;
    private String subject;
    private String emailBody;

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public String getSubject() {
        return subject;
    }

    public String getEmailBody() {
        return emailBody;
    }

    private MailSender() {
    }

    public static Builder newBuilder() {
        return new MailSender().new Builder();
    }

    public void sendMail() throws MessagingException {
        Properties mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", "587");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");
        try{
            Session getMailSession = Session.getDefaultInstance(mailServerProperties, null);
            MimeMessage generateMailMessage = new MimeMessage(getMailSession);
            generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(recipientEmail));
            generateMailMessage.setSubject(subject);
            generateMailMessage.setContent(emailBody, "text/html");
            Transport transport = getMailSession.getTransport("smtp");
            transport.connect("smtp.gmail.com", username, password);
            transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
            transport.close();
        } catch(MessagingException up){
            throw up;
        }
    }

    public class Builder {

        private Builder(){

        }

        public Builder setRecipientEmail(String recipientEmail){
            MailSender.this.recipientEmail = recipientEmail;
            return this;
        }

        public Builder setSubject(String subject){
            MailSender.this.subject = subject;
            return this;
        }

        public  Builder setEmailBody(String emailBody){
            MailSender.this.emailBody = emailBody;
            return this;
        }

        public MailSender build(){
            return MailSender.this;
        }
    }


}
