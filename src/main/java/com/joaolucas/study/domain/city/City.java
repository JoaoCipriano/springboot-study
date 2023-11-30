package com.joaolucas.study.domain.city;

import com.joaolucas.study.infrastructure.database.city.CityEntity;

public record City(
        Integer id,
        String name) {

    public City(CityEntity cityEntity) {
        this(cityEntity.getId(), cityEntity.getName());
    }
}
