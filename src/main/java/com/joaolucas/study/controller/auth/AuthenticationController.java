package com.joaolucas.study.controller.auth;

import com.joaolucas.study.controller.auth.model.AuthenticationRequest;
import com.joaolucas.study.controller.auth.model.AuthenticationResponse;
import com.joaolucas.study.domain.auth.AuthenticationService;
import com.joaolucas.study.controller.auth.model.RegisterRequest;
import com.joaolucas.study.infrastructure.email.model.Email;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping(value = "/refresh_token")
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        authenticationService.refreshToken(response);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/forgot")
    public ResponseEntity<Void> forgot(@Valid @RequestBody Email objDTO) {
        authenticationService.sendNewPassword(objDTO.email());
        return ResponseEntity.noContent().build();
    }
}
