package com.joaolucas.study.infrastructure.database.city;

import com.joaolucas.study.infrastructure.database.state.StateEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Entity
public class CityEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private StateEntity state;

    public CityEntity() {
    }

    public CityEntity(Integer id, String name, StateEntity state) {
        super();
        this.id = id;
        this.name = name;
        this.state = state;
    }
}
