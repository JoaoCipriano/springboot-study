package com.joaolucas.study.infrastructure.email;

import com.joaolucas.study.infrastructure.database.customer.CustomerEntity;
import com.joaolucas.study.infrastructure.database.order.OrderEntity;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;

@RequiredArgsConstructor
public abstract class AbstractEmailService implements EmailService {

    private final TemplateEngine templateEngine;

    private final JavaMailSender javaMailSender;

    @Value("${default.sender}")
    private String sender;

    @Override
    public void sendOrderConfirmationEmail(OrderEntity obj) {
        var sm = prepareSimpleMailMessageFromPedido(obj);
        sendEmail(sm);
    }

    protected SimpleMailMessage prepareSimpleMailMessageFromPedido(OrderEntity obj) {
        var sm = new SimpleMailMessage();
        sm.setTo(obj.getCustomer().getEmail());
        sm.setFrom(sender);
        sm.setSubject("Pedido confirmado! Código: " + obj.getId());
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText(obj.toString());
        return sm;
    }

    protected String htmlFromTemplatePedido(OrderEntity obj) {
        var context = new Context();
        context.setVariable("pedido", obj);
        return templateEngine.process("email/confirmacaoPedido", context);
    }

    @Override
    public void sendOrderConfirmationHtmlEmail(OrderEntity obj) {
        try {
            var mm = prepareMimeMessageFromPedido(obj);
            sendHtmlEmail(mm);
        } catch (MessagingException e) {
            sendOrderConfirmationEmail(obj);
        }
    }

    protected MimeMessage prepareMimeMessageFromPedido(OrderEntity obj) throws MessagingException {
        var mimeMessage = javaMailSender.createMimeMessage();
        var mmh = new MimeMessageHelper(mimeMessage, true);
        mmh.setTo(obj.getCustomer().getEmail());
        mmh.setFrom(sender);
        mmh.setSubject("Pedido confirmado! Código: " + obj.getId());
        mmh.setSentDate(new Date(System.currentTimeMillis()));
        mmh.setText(htmlFromTemplatePedido(obj), true);
        return mimeMessage;
    }

    @Override
    public void sendNewPasswordEmail(CustomerEntity customerEntity, String newPass) {
        SimpleMailMessage sm = prepareNewPasswordEmail(customerEntity, newPass);
        sendEmail(sm);
    }

    protected SimpleMailMessage prepareNewPasswordEmail(CustomerEntity customerEntity, String newPass) {
        var sm = new SimpleMailMessage();
        sm.setTo(customerEntity.getEmail());
        sm.setFrom(sender);
        sm.setSubject("Solicitação de nova senha");
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText("Nova senha: " + newPass);
        return sm;
    }
}
