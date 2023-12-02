package com.joaolucas.study.domain.order;

import com.joaolucas.study.controller.order.model.OrderRequest;
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
import com.joaolucas.study.infrastructure.database.orderitem.OrderItemEntity;
import com.joaolucas.study.infrastructure.database.payment.PaymentStatus;
import com.joaolucas.study.infrastructure.database.payment.PaymentWithSlipEntity;
import com.joaolucas.study.infrastructure.database.user.UserEntity;
import com.joaolucas.study.infrastructure.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

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
                        "Objeto não encontrado Id: " + id + ", Tipo: " + OrderEntity.class.getName()));
    }

    @Transactional
    public OrderEntity insert(OrderEntity orderEntity) {
        orderEntity.setId(null);
        orderEntity.setInstant(new Date());
        orderEntity.setCustomer(customerService.findEntityById(orderEntity.getCustomer().getId()));
        orderEntity.getPayment().setState(PaymentStatus.PENDING);
        orderEntity.getPayment().setOrder(orderEntity);
        if (orderEntity.getPayment() instanceof PaymentWithSlipEntity pagto) {
            bankSlipService.preencherPagamentoComBoleto(pagto, orderEntity.getInstant());
        }
        orderEntity = orderRepository.save(orderEntity);
        paymentService.insert(orderEntity.getPayment());
        for (OrderItemEntity ip : orderEntity.getItens()) {
            ip.setDiscount(0.0);
            ip.setProduct(productService.find(ip.getProduct().getId()));
            ip.setAmount(ip.getProduct().getPrice());
            ip.setOrder(orderEntity);
        }
        orderItemService.insertListPedidos(orderEntity.getItens());
        emailService.sendOrderConfirmationHtmlEmail(orderEntity);
        return orderEntity;
    }

    public OrderEntity fromDTO(OrderRequest orderRequest) {
        var orderEntity = new OrderEntity();

//        var cliente = CustomerEn;

//        var addressEntity = new AddressEntity(orderRequest.shippingAddress());

        Set<OrderItemEntity> itens = orderRequest.items().stream()
                .map(OrderItemEntity::new)
                .collect(Collectors.toSet());

//        orderEntity.setCustomer(cliente);
//        orderEntity.setShippingAddress(addressEntity);
        orderEntity.setItens(itens);
        orderEntity.setPayment(orderRequest.paymentEntity());
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
}
