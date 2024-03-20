package com.joaolucas.study.domain.auth;

import com.joaolucas.model.AuthenticationRequest;
import com.joaolucas.model.AuthenticationResponse;
import com.joaolucas.model.RegisterRequest;
import com.joaolucas.study.domain.exceptions.AuthorizationException;
import com.joaolucas.study.domain.exceptions.ObjectNotFoundException;
import com.joaolucas.study.domain.jwt.JwtService;
import com.joaolucas.study.domain.user.UserService;
import com.joaolucas.study.infrastructure.database.user.Role;
import com.joaolucas.study.infrastructure.database.user.UserEntity;
import com.joaolucas.study.infrastructure.database.user.UserRepository;
import com.joaolucas.study.infrastructure.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
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
    private final SecureRandom rand = new SecureRandom();

    public AuthenticationResponse register(RegisterRequest request) {
        var user = UserEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of(Role.USER.ordinal()))
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse().token(jwtToken);
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
        return new AuthenticationResponse().token(jwtToken);
    }

    public AuthenticationResponse refreshToken() {
        var user = UserService.authenticated();
        if (user == null)
            throw new AuthorizationException("Access denied");
        var token = jwtService.generateToken(user);
        return new AuthenticationResponse().token(token);
    }

    public void sendNewPassword(String email) {
        var userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new ObjectNotFoundException("Email was not found"));

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
        return switch (opt) {
            case 0 -> generateOneDigit();
            case 1 -> generateOneUpperCaseLetter();
            default -> generateOneLowerCaseLetter();
        };
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
