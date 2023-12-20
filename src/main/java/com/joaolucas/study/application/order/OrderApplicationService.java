package com.joaolucas.study.application.order;

import com.joaolucas.study.application.order.mapper.OrderMapper;
import com.joaolucas.study.controller.order.model.OrderRequest;
import com.joaolucas.study.controller.order.model.OrderResponse;
import com.joaolucas.study.domain.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderApplicationService {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    public OrderResponse find(Integer id) {
        return orderMapper.toResponse(orderService.find(id));
    }

    public Page<OrderResponse> findPage(Integer page,
                                        Integer linesPerPage,
                                        String orderBy,
                                        String direction) {
        return orderMapper.toPageableResponse(orderService.findPage(page, linesPerPage, orderBy, direction));
    }

    public OrderResponse insert(OrderRequest orderRequest) {
        var orderEntity = orderService.insert(orderMapper.toEntity(orderRequest));
        return orderMapper.toResponse(orderEntity);
    }
}
