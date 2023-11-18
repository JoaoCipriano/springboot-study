package com.joaolucas.study.services;

import com.joaolucas.study.domain.ItemPedido;
import com.joaolucas.study.repositories.ItemPedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ItemPedidoService {

    private final ItemPedidoRepository repo;

    public void insertListPedidos(Set<ItemPedido> itemPedidos) {
        repo.saveAll(itemPedidos);
    }
}
