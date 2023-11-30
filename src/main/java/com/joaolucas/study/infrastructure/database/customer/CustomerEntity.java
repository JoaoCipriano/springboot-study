package com.joaolucas.study.infrastructure.database.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.joaolucas.study.infrastructure.database.order.OrderEntity;
import com.joaolucas.study.infrastructure.database.user.UserEntity;
import com.joaolucas.study.domain.user.Customer;
import com.joaolucas.study.infrastructure.database.user.Role;
import com.joaolucas.study.infrastructure.database.address.AddressEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class CustomerEntity extends UserEntity {

    private String socialId;
    private Integer type;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<AddressEntity> addressEntities = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "PHONES")
    private Set<String> phones = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<OrderEntity> orders = new ArrayList<>();

    public CustomerEntity() {
        addRole(Role.USER);
    }

    public CustomerEntity(Integer id, String firstName, String lastName, String email, String socialId, CustomerType type, String password) {
        super(id, firstName, lastName, email, password, buildRoles(Role.USER));
        this.socialId = socialId;
        this.type = type.getCod();
        addRole(Role.USER);
    }

    public CustomerEntity(Customer customer) {
        super(customer.id(), customer.firstName(), customer.lastName(), null, null, Collections.emptySet());
    }
}
