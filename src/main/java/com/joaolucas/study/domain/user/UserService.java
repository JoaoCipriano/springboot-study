package com.joaolucas.study.domain.user;

import com.joaolucas.study.domain.exceptions.ObjectNotFoundException;
import com.joaolucas.study.infrastructure.database.user.UserEntity;
import com.joaolucas.study.infrastructure.database.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ObjectNotFoundException("Object not found"));
    }

    public static UserEntity authenticated() {
        try {
            return (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }
}