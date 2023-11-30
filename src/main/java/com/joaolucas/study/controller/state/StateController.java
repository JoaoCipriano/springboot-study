package com.joaolucas.study.controller.state;

import com.joaolucas.study.infrastructure.database.city.CityEntity;
import com.joaolucas.study.infrastructure.database.state.StateEntity;
import com.joaolucas.study.domain.city.City;
import com.joaolucas.study.domain.state.State;
import com.joaolucas.study.domain.city.CityService;
import com.joaolucas.study.domain.state.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/estados")
@RequiredArgsConstructor
public class StateController {

    private final StateService service;

    private final CityService cityService;

    @GetMapping
    public ResponseEntity<List<State>> findAll() {
        List<StateEntity> list = service.findAll();
        List<State> listDto = list.stream().map(State::new).toList();
        return ResponseEntity.ok(listDto);
    }

    @GetMapping(value = "/{estadoId}/cidades")
    public ResponseEntity<List<City>> findCidades(@PathVariable Integer estadoId) {
        List<CityEntity> list = cityService.findByEstado(estadoId);
        List<City> listDto = list.stream().map(City::new).toList();
        return ResponseEntity.ok(listDto);
    }
}
