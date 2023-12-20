package com.joaolucas.study.infrastructure.email;

import com.joaolucas.study.infrastructure.database.order.OrderEntity;
import com.joaolucas.study.infrastructure.database.user.UserEntity;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void sendOrderConfirmationEmail(OrderEntity obj);

    void sendEmail(SimpleMailMessage msg);

    void sendOrderConfirmationHtmlEmail(OrderEntity obj);

    void sendHtmlEmail(MimeMessage msg);

    void sendNewPasswordEmail(UserEntity userEntity, String newPassword);
}
