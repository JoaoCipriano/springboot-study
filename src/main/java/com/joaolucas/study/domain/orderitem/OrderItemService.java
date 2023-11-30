package com.joaolucas.study.domain.orderitem;

import com.joaolucas.study.infrastructure.database.orderitem.OrderItemEntity;
import com.joaolucas.study.infrastructure.database.orderitem.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository repo;

    public void insertListPedidos(Set<OrderItemEntity> orderItemEntities) {
        repo.saveAll(orderItemEntities);
    }
}
