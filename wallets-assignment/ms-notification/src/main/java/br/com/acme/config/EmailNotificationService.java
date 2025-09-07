package br.com.acme.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

import java.math.BigDecimal;

@Component
@AllArgsConstructor
public class EmailNotificationService implements NotificationClient{

    private final SesClient sesClient;

    @Override
    public void notification(String toEmail, String codeTransaction, BigDecimal amount) {
        String subject = " Notification Transaction ";
        String body = " Transaction CODE :  " + codeTransaction + " Amount R$ : " + new BigDecimal(String.valueOf(amount));

        SendEmailRequest emailRequest = SendEmailRequest.builder()
                .destination(Destination.builder()
                        .toAddresses(toEmail)
                        .build())
                .message(Message.builder()
                        .subject(Content.builder().data(subject).charset("UTF-8").build())
                        .body(Body.builder()
                                .text(Content.builder().data(body).charset("UTF-8").build())
                                .build())
                        .build())
                .source("cbarbosagomesfilho@gmail.com")
                .build();

        sesClient.sendEmail(emailRequest);
        System.out.println("E-mail send successfully");
    }
}
