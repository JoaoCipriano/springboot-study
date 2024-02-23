package com.joaolucas.study.controller.auth;

import com.joaolucas.api.AuthenticationApi;
import com.joaolucas.model.AuthenticationRequest;
import com.joaolucas.model.AuthenticationResponse;
import com.joaolucas.model.ForgotPasswordRequest;
import com.joaolucas.model.RegisterRequest;
import com.joaolucas.study.domain.auth.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationApi {

    private final AuthenticationService authenticationService;

    @Override
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @Override
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @Override
    public ResponseEntity<AuthenticationResponse> refreshToken() {
        return ResponseEntity.ok(authenticationService.refreshToken());
    }

    @Override
    public ResponseEntity<Void> forgot(@Valid @RequestBody ForgotPasswordRequest request) {
        authenticationService.sendNewPassword(request.getEmail());
        return ResponseEntity.noContent().build();
    }
}
