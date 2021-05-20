package com.joaolucas.cursomc.resources;

import com.joaolucas.cursomc.dto.EmailDTO;
import com.joaolucas.cursomc.security.JWTUtil;
import com.joaolucas.cursomc.security.UserSS;
import com.joaolucas.cursomc.services.AuthService;
import com.joaolucas.cursomc.services.UserService;
import com.joaolucas.cursomc.services.exceptions.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthService service;

    @PostMapping(value = "/refresh_token")
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        UserSS user = UserService.authenticated();
        if (user == null)
            throw new AuthorizationException("Acesso negado");
        String token = jwtUtil.generateToken(user.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/forgot")
    public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDTO) {
        service.sendNewPassword(objDTO.getEmail());
        return ResponseEntity.noContent().build();
    }
}
