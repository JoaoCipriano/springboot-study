package com.joaolucas.study.infrastructure.email;

import com.joaolucas.study.infrastructure.database.customer.CustomerEntity;
import com.joaolucas.study.infrastructure.database.order.OrderEntity;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void sendOrderConfirmationEmail(OrderEntity obj);

    void sendEmail(SimpleMailMessage msg);

    void sendOrderConfirmationHtmlEmail(OrderEntity obj);

    void sendHtmlEmail(MimeMessage msg);

    void sendNewPasswordEmail(CustomerEntity customerEntity, String newPass);
}
