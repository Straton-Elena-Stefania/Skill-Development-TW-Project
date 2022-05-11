package com.timetraveling.utils.async.push;

import com.timetraveling.models.email.Email;
import com.timetraveling.utils.async.QueueSubscriber;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Clasa aceasta actioneaza ca un daemon in cadrul serverului,
 * pentru a trimite emailuri cu update-uri catre utilizatori.
 * Clasa asculta si raspunde la update-urile ce vin de la microservicii
 * in mod asincron
 */
public class EmailTask implements Runnable, EmailSending, Routing {
    /**
     * Aceasta este o lista cu wildcard-uri pe care aplicatia le
     * asculta pentru a primi mesaje ale caror chei de rutare se potrivesc
     * cu ceea ce ascultam noi.
     */
    private static final List<String> BINDING_KEYS = new ArrayList<>();

    /**
     * Aceasta este sesiunea stabilita cu gmail.
     */
    private Session session;
    /**
     * Pentru a deschide si mentine conexiunea cu brokerul de mesaje.
     */
    private QueueSubscriber queueSubscriber;

    /**
     * Actiunea pe care o ia threadul atunci cand il punem sa ruleze
     */
    @Override
    public void run() {
        /**
         * Dam subscribe pe topicurile pe care vrem sa le transmitem
         * mai departe utilizatorilor.
         */
        BINDING_KEYS.add(STEP_ROUTE);
        BINDING_KEYS.add(SUBSECTION_ROUTE);
        this.queueSubscriber = new QueueSubscriber(this);

        try {
            /**
             * Devenim clienti RabbitMQ.
             */
            queueSubscriber.initializeConnection();
            queueSubscriber.subscribe(BINDING_KEYS);
        } catch (IOException | TimeoutException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Aceasta functie stabileste o sesiune intre Gmail si aplicatie prin SMTP.
     * Conexiunea este prin SSL.
     * Conexiunea se face catre contul oficial SKIVI, de pe care mailurile sunt
     * trimise catre utilizatori
     */
    public void establishSession() {
        Properties properties = new Properties();

        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        this.session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(EMAIL_SENDER, PASSWORD);
                    }
                });
    }

    /**
     * O functie atomica ce trimite un singur email catre o adresa
     * @param email Un obiect email ce descrie structura emailului.
     * @param receiverAddress Adresa ce va primi emailul.
     */
    public void sendEmail(Email email, String receiverAddress) {
        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(EMAIL_SENDER));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(receiverAddress)
            );

            message.setSubject(email.getSubject());
            message.setText(email.getBody());

            Transport.send(message);

            System.out.println("Sent email to " + receiverAddress);
        } catch (MessagingException messagingException) {
            throw new RuntimeException(messagingException);
        }
    }


    public void endProcess() {
        if (queueSubscriber != null) {

        }
    }
}
