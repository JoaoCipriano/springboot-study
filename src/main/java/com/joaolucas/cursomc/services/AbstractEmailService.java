package com.joaolucas.cursomc.services;

import com.joaolucas.cursomc.domain.Cliente;
import com.joaolucas.cursomc.domain.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

public  abstract class AbstractEmailService implements EmailService {

    @Value("${default.sender}")
    private String sender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendOrderConfirmationEmail(Pedido obj) {
        var sm = prepareSimpleMailMessageFromPedido(obj);
        sendEmail(sm);
    }

    protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
        var sm = new SimpleMailMessage();
        sm.setTo(obj.getCliente().getEmail());
        sm.setFrom(sender);
        sm.setSubject("Pedido confirmado! Código: " + obj.getId());
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText(obj.toString());
        return sm;
    }

    protected String htmlFromTemplatePedido(Pedido obj) {
        var context = new Context();
        context.setVariable("pedido", obj);
        return templateEngine.process("email/confirmacaoPedido", context);
    }

    @Override
    public void sendOrderConfirmationHtmlEmail(Pedido obj) {
        try {
            var mm = prepareMimeMessageFromPedido(obj);
            sendHtmlEmail(mm);
        }
        catch (MessagingException e) {
            sendOrderConfirmationEmail(obj);
        }
    }

    protected MimeMessage prepareMimeMessageFromPedido(Pedido obj) throws MessagingException {
        var mimeMessage = javaMailSender.createMimeMessage();
        var mmh = new MimeMessageHelper(mimeMessage, true);
        mmh.setTo(obj.getCliente().getEmail());
        mmh.setFrom(sender);
        mmh.setSubject("Pedido confirmado! Código: " + obj.getId());
        mmh.setSentDate(new Date(System.currentTimeMillis()));
        mmh.setText(htmlFromTemplatePedido(obj), true);
        return mimeMessage;
    }

    @Override
    public void sendNewPasswordEmail(Cliente cliente, String newPass) {
        SimpleMailMessage sm = prepareNewPasswordEmail(cliente, newPass);
        sendEmail(sm);
    }

    protected SimpleMailMessage prepareNewPasswordEmail(Cliente cliente, String newPass) {
        var sm = new SimpleMailMessage();
        sm.setTo(cliente.getEmail());
        sm.setFrom(sender);
        sm.setSubject("Solicitação de nova senha");
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText("Nova senha: " + newPass);
        return sm;
    }
}
