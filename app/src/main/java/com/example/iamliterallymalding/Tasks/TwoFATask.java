package com.example.iamliterallymalding.Tasks;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class TwoFATask implements Runnable {

    private final String from, host, to;
    private final Properties props;
    private final Session session;
    private final MimeMessage msg;
    protected final MutableLiveData<Integer> output;

    public TwoFATask(String email) {
        from = "DFinger@2FA.com";
        to = email;
        host = "smtp.mailtrap.io";
        props = System.getProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "2525");
        session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("3726ee9e069855", "f2c57fda83dbe2");
                    }
                });
        msg = new MimeMessage(session);
        output = new MutableLiveData<>();
    }

    public LiveData<Integer> getOutput () {return output;}

    @Override
    public void run() {
        System.out.println(to);
        Random random = new Random();
        try {
            int code = random.nextInt(50000);
            msg.setFrom(new InternetAddress(from));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSubject("2FA request");
            msg.setText("Type this code into your device: " + code);
            Transport.send(msg);
            output.postValue(code);
        } catch (MessagingException e) {
            e.printStackTrace();
            output.postValue(-1);
        }
    }
}


