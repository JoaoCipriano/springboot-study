package com.joaolucas.study.domain.auth;

import com.joaolucas.study.controller.auth.model.AuthenticationRequest;
import com.joaolucas.study.controller.auth.model.AuthenticationResponse;
import com.joaolucas.study.controller.auth.model.RegisterRequest;
import com.joaolucas.study.domain.jwt.JwtService;
import com.joaolucas.study.infrastructure.database.user.UserEntity;
import com.joaolucas.study.infrastructure.database.user.Role;
import com.joaolucas.study.infrastructure.database.user.UserRepository;
import com.joaolucas.study.infrastructure.email.EmailService;
import com.joaolucas.study.domain.user.UserService;
import com.joaolucas.study.domain.exceptions.AuthorizationException;
import com.joaolucas.study.domain.exceptions.ObjectNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final EmailService emailService;
    private final Random rand = new Random();

    public AuthenticationResponse register(RegisterRequest request) {
        var user = UserEntity.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of(Role.USER.ordinal()))
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ObjectNotFoundException("Failed to find"));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public void refreshToken(HttpServletResponse response) {
        var user = UserService.authenticated();
        if (user == null)
            throw new AuthorizationException("Acesso negado");
        var token = jwtService.generateToken(user);
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("access-control-expose-headers", "Authorization");
    }

    public void sendNewPassword(String email) {
        var userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new ObjectNotFoundException("Email não encontrado"));

        var newPassword = newPassword();
        userEntity.setPassword(passwordEncoder.encode(newPassword));

        userService.save(userEntity);
        emailService.sendNewPasswordEmail(userEntity, newPassword);
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
