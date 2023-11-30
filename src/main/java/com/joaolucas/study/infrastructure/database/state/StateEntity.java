package com.joaolucas.study.infrastructure.database.state;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.joaolucas.study.infrastructure.database.city.CityEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class StateEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "state")
    private List<CityEntity> cityEntities = new ArrayList<>();

    public StateEntity() {
    }

    public StateEntity(Integer id, String name) {
        super();
        this.id = id;
        this.name = name;
    }
}
