package com.joaolucas.study.infrastructure.database.customer;

import com.joaolucas.study.infrastructure.database.address.AddressEntity;
import com.joaolucas.study.infrastructure.database.order.OrderEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -1106622878998441169L;

    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private String email;
    private String socialId;
    private Integer type;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<AddressEntity> addresses = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "PHONES")
    private Set<String> phones = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    private List<OrderEntity> orders = new ArrayList<>();

    public CustomerEntity(String email, String socialId, Integer type) {
        this.email = email;
        this.socialId = socialId;
        this.type = type;
    }
}
