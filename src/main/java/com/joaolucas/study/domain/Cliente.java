package com.joaolucas.study.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.joaolucas.study.domain.enums.CustomerType;
import com.joaolucas.study.dto.ClienteDTO;
import com.joaolucas.study.security.Role;
import com.joaolucas.study.security.User;
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
public class Cliente extends User {

    private String socialId;
    private Integer type;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Endereco> enderecos = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "TELEFONE")
    private Set<String> telefones = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos = new ArrayList<>();

    public Cliente() {
        addRole(Role.USER);
    }

    public Cliente(Integer id, String firstName, String lastName, String email, String socialId, CustomerType type, String password) {
        super(id, firstName, lastName, email, password, buildRoles(Role.USER));
        this.socialId = socialId;
        this.type = type.getCod();
        addRole(Role.USER);
    }

    public Cliente(ClienteDTO clienteDTO) {
        super(clienteDTO.id(), clienteDTO.firstName(), clienteDTO.lastName(), null, null, Collections.emptySet());
    }
}
