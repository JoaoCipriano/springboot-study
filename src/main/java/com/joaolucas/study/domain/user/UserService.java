package com.joaolucas.study.domain.user;

import com.joaolucas.study.infrastructure.database.user.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserService {

    private UserService() {
    }

    public static UserEntity authenticated() {
        try {
            return (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }
}