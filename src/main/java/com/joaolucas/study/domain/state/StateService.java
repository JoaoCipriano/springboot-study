package com.joaolucas.study.domain.state;

import com.joaolucas.study.infrastructure.database.state.StateEntity;
import com.joaolucas.study.infrastructure.database.state.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StateService {

    private final StateRepository repository;

    public List<StateEntity> findAll() {
        return repository.findAllByOrderByName();
    }
}
