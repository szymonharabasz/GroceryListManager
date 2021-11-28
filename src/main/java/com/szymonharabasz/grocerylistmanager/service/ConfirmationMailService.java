package com.szymonharabasz.grocerylistmanager.service;

import com.szymonharabasz.grocerylistmanager.domain.User;
import org.apache.commons.lang3.RandomStringUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.ServletContext;
import java.util.Properties;

@Named
@ApplicationScoped
public class ConfirmationMailService {

    @Inject
    private ServletContext servletContext;

    public void sendEmail(User user) throws MessagingException {
        String host = servletContext.getInitParameter("mail.smtp.host");
        String port = servletContext.getInitParameter("mail.smtp.port");

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.ssl.trust",host);
        properties.put("mail.smtp.socketFactory.port", port);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");

        String username = servletContext.getInitParameter("mail.smtp.username");
        String password = servletContext.getInitParameter("mail.smtp.password");

        System.err.println("HOST " + host);

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        String address = servletContext.getInitParameter("mail.smtp.address");
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(address));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
        message.setSubject("[Grocery List Manager] Confirm your email.");

        String contextPath = servletContext.getContextPath();
        // TODO: change to real domain
        String webserver = "localhost:8080";
        String confirmationToken = user.getConfirmationToken();
        String link = webserver + contextPath + "/confirm.xhtml?token=" + confirmationToken;

        String msg = "Hello, " + user.getName() + "!\n\n" +
                "to confirm you e-mail address in the Grocery List Manager application, " +
                "please click on the link below or copy it to " +
                "your web browser address bar.\n\n" +
                link + "\n\n" +
                "This helps use to make sure that you are a real personn and that your address " +
                "can be used for password recovery should you need that. If you fail to confirm your e-mail," +
                "your acount will be removed after 48 hours.\n\n" +
                "Your Grocery List Manager Team";
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/plain; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message);
    }
}
