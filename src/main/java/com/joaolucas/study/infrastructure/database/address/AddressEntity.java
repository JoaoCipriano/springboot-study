package com.joaolucas.study.infrastructure.database.address;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.joaolucas.study.infrastructure.database.city.CityEntity;
import com.joaolucas.study.infrastructure.database.customer.CustomerEntity;
import com.joaolucas.study.domain.order.Address;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_address")
public class AddressEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String publicPlace;
    private String number;
    private String complement;
    private String neighborhood;
    private String zipCode;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private CityEntity cityEntity;

    public AddressEntity(Address address) {
        this.id = address.id();
        this.publicPlace = address.publicPlace();
        this.number = address.number();
        this.complement = address.complement();
        this.neighborhood = address.neighborhood();
        this.zipCode = address.zipCode();
    }
}
