package com.joaolucas.study.domain.order;

import com.joaolucas.study.domain.payment.BankSlipService;
import com.joaolucas.study.domain.orderitem.OrderItemService;
import com.joaolucas.study.domain.payment.PaymentService;
import com.joaolucas.study.domain.customer.CustomerService;
import com.joaolucas.study.domain.product.ProductService;
import com.joaolucas.study.domain.user.UserService;
import com.joaolucas.study.infrastructure.database.customer.CustomerEntity;
import com.joaolucas.study.infrastructure.database.address.AddressEntity;
import com.joaolucas.study.infrastructure.database.orderitem.OrderItemEntity;
import com.joaolucas.study.infrastructure.database.payment.PaymentWithSlipEntity;
import com.joaolucas.study.infrastructure.database.order.OrderEntity;
import com.joaolucas.study.infrastructure.database.payment.PaymentStatus;
import com.joaolucas.study.infrastructure.email.EmailService;
import com.joaolucas.study.infrastructure.database.order.OrderRepository;
import com.joaolucas.study.infrastructure.database.user.UserEntity;
import com.joaolucas.study.domain.exceptions.AuthorizationException;
import com.joaolucas.study.domain.exceptions.ObjectNotFoundException;
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

    private final OrderRepository repo;

    private final BankSlipService bankSlipService;

    private final PaymentService paymentService;

    private final ProductService productService;

    private final OrderItemService orderItemService;

    private final CustomerService customerService;

    private final EmailService emailService;

    public OrderEntity find(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Objeto não encontrado Id: " + id + ", Tipo: " + OrderEntity.class.getName()));
    }

    @Transactional
    public OrderEntity insert(OrderEntity obj) {
        obj.setId(null);
        obj.setInstant(new Date());
        obj.setCustomer(customerService.find(obj.getCustomer().getId()));
        obj.getPaymentEntity().setState(PaymentStatus.PENDING);
        obj.getPaymentEntity().setOrder(obj);
        if (obj.getPaymentEntity() instanceof PaymentWithSlipEntity pagto) {
            bankSlipService.preencherPagamentoComBoleto(pagto, obj.getInstant());
        }
        obj = repo.save(obj);
        paymentService.insert(obj.getPaymentEntity());
        for (OrderItemEntity ip : obj.getItens()) {
            ip.setDiscount(0.0);
            ip.setProduct(productService.find(ip.getProduct().getId()));
            ip.setAmount(ip.getProduct().getPrice());
            ip.setOrder(obj);
        }
        orderItemService.insertListPedidos(obj.getItens());
        emailService.sendOrderConfirmationHtmlEmail(obj);
        return obj;
    }

    public OrderEntity fromDTO(NewOrder newOrder) {
        var pedido = new OrderEntity();

        var cliente = new CustomerEntity(newOrder.customer());

        var endereco = new AddressEntity(newOrder.shippingAddress());

        Set<OrderItemEntity> itens = newOrder.items().stream().map(OrderItemEntity::new).collect(Collectors.toSet());

        pedido.setCustomer(cliente);
        pedido.setShippingAddress(endereco);
        pedido.setItens(itens);
        pedido.setPaymentEntity(newOrder.paymentEntity());
        return pedido;
    }

    public Page<OrderEntity> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        UserEntity user = UserService.authenticated();
        if (user == null) {
            throw new AuthorizationException("Acesso Negado");
        }
        var pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        var cliente = customerService.find(user.getId());
        return repo.findByCustomer(cliente, pageRequest);
    }
}
