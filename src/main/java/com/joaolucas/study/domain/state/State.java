package com.joaolucas.study.domain.state;

import com.joaolucas.study.infrastructure.database.state.StateEntity;

public record State(
        Integer id,
        String name) {

    public State(StateEntity obj) {
        this(obj.getId(), obj.getName());
    }
}
