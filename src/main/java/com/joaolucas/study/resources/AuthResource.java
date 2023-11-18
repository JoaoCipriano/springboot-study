package com.joaolucas.study.resources;

import com.joaolucas.study.config.JwtService;
import com.joaolucas.study.dto.EmailDTO;
import com.joaolucas.study.services.AuthService;
import com.joaolucas.study.services.UserService;
import com.joaolucas.study.services.exceptions.AuthorizationException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AuthResource {

    private final JwtService jwtUtil;

    private final AuthService service;

    @PostMapping(value = "/refresh_token")
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        var user = UserService.authenticated();
        if (user == null)
            throw new AuthorizationException("Acesso negado");
        var token = jwtUtil.generateToken(user);
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("access-control-expose-headers", "Authorization");
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/forgot")
    public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDTO) {
        service.sendNewPassword(objDTO.email());
        return ResponseEntity.noContent().build();
    }
}
