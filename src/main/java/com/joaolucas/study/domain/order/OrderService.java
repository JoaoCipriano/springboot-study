package com.joaolucas.study.domain.order;

import com.amazonaws.util.CollectionUtils;
import com.joaolucas.study.domain.customer.CustomerService;
import com.joaolucas.study.domain.exceptions.AuthorizationException;
import com.joaolucas.study.domain.exceptions.ObjectNotFoundException;
import com.joaolucas.study.domain.orderitem.OrderItemService;
import com.joaolucas.study.domain.payment.BankSlipService;
import com.joaolucas.study.domain.payment.PaymentService;
import com.joaolucas.study.domain.product.ProductService;
import com.joaolucas.study.domain.user.UserService;
import com.joaolucas.study.infrastructure.database.order.OrderEntity;
import com.joaolucas.study.infrastructure.database.order.OrderRepository;
import com.joaolucas.study.infrastructure.database.payment.PaymentWithSlipEntity;
import com.joaolucas.study.infrastructure.database.user.UserEntity;
import com.joaolucas.study.infrastructure.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final BankSlipService bankSlipService;
    private final PaymentService paymentService;
    private final ProductService productService;
    private final OrderItemService orderItemService;
    private final CustomerService customerService;
    private final EmailService emailService;

    public OrderEntity find(Integer id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Object not Found, Id: " + id + ", Type: " + OrderEntity.class.getName()));
    }

    @Transactional
    public OrderEntity insert(OrderEntity orderEntity) {
        orderEntity.setCustomer(customerService.findEntityById(orderEntity.getCustomer().getId()));
        orderEntity.getPayment().setOrder(orderEntity);
        addDueDateIfNeeded(orderEntity);
        orderEntity = orderRepository.save(orderEntity);
        paymentService.save(orderEntity.getPayment());
        saveOrderItems(orderEntity);
        emailService.sendOrderConfirmationHtmlEmail(orderEntity);
        return orderEntity;
    }

    public Page<OrderEntity> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        UserEntity user = UserService.authenticated();
        if (user == null) {
            throw new AuthorizationException("Acesso Negado");
        }
        var pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        var cliente = customerService.findEntityById(user.getId());
        return orderRepository.findByCustomer(cliente, pageRequest);
    }

    private void addDueDateIfNeeded(OrderEntity orderEntity) {
        if (orderEntity.getPayment() instanceof PaymentWithSlipEntity paymentWithSlip) {
            bankSlipService.addDueDate(paymentWithSlip, orderEntity.getInstant());
        }
    }

    private void saveOrderItems(OrderEntity orderEntity) {
        if (!CollectionUtils.isNullOrEmpty(orderEntity.getItems())) {
            orderEntity.getItems().forEach(orderItem -> {
                orderItem.setDiscount(BigDecimal.ZERO);
                orderItem.setProduct(productService.find(orderItem.getProduct().getId()));
                orderItem.setAmount(orderItem.getProduct().getPrice());
                orderItem.setOrder(orderEntity);

            });
            orderItemService.saveAll(orderEntity.getItems());
        }
    }
}
