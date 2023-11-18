package com.joaolucas.study.services;

import com.joaolucas.study.security.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserService {

    private UserService() {
    }

    public static User authenticated() {
        try {
            return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }
}