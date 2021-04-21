package com.joaolucas.cursomc.services;

import com.joaolucas.cursomc.domain.ItemPedido;
import com.joaolucas.cursomc.repositories.ItemPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ItemPedidoService {

    @Autowired
    private ItemPedidoRepository repo;

    public void insertListPedidos(Set<ItemPedido> itemPedidos) {
        repo.saveAll(itemPedidos);
    }
}
