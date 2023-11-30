package com.joaolucas.study.domain.city;

import com.joaolucas.study.infrastructure.database.city.CityEntity;
import com.joaolucas.study.infrastructure.database.city.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository repository;

    public List<CityEntity> findByEstado(Integer estadoId) {
       return  repository.findAllByStateId(estadoId);
    }
}
