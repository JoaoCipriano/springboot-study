package com.joaolucas.study.services;

import com.joaolucas.study.domain.Cliente;
import com.joaolucas.study.services.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final ClienteService clienteService;

    private final BCryptPasswordEncoder passwordEncoder;

    private final EmailService emailService;

    private final Random rand = new Random();

    public void sendNewPassword(String email) {
        Cliente cliente = clienteService.findByEmail(email);

        if (cliente == null) {
            throw new ObjectNotFoundException("Email não encontrado");
        }

        String newPass = newPassword();
        cliente.setPassword(passwordEncoder.encode(newPass));

        clienteService.insertOrUpdate(cliente);
        emailService.sendNewPasswordEmail(cliente, newPass);
    }

    private String newPassword() {
        char[] vet = new char[10];
        for (int i = 0; i < 10; i++) {
            vet[i] = randomChar();
        }
        return new String(vet);
    }

    private char randomChar() {
        int opt = rand.nextInt(3);
        if (opt == 0) {
            return generateOneDigit();
        } else if (opt == 1) {
            return generateOneUpperCaseLetter();
        } else {
            return generateOneLowerCaseLetter();
        }
    }

    private char generateOneDigit() {
        return (char) (rand.nextInt(10) + 48);
    }

    private char generateOneUpperCaseLetter() {
        return (char) (rand.nextInt(26) + 65);
    }

    private char generateOneLowerCaseLetter() {
        return (char) (rand.nextInt(26) + 97);
    }
}
