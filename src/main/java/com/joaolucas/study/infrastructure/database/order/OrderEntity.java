package com.joaolucas.study.infrastructure.database.order;

import com.joaolucas.study.infrastructure.database.address.AddressEntity;
import com.joaolucas.study.infrastructure.database.customer.CustomerEntity;
import com.joaolucas.study.infrastructure.database.orderitem.OrderItemEntity;
import com.joaolucas.study.infrastructure.database.payment.PaymentEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class OrderEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime instant;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "order")
    private PaymentEntity payment;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @ManyToOne
    @JoinColumn(name = "shipping_address_id")
    private AddressEntity shippingAddress;

    @OneToMany(mappedBy = "id.order")
    private Set<OrderItemEntity> items = new HashSet<>();

    public OrderEntity() {
    }

    public OrderEntity(Integer id, LocalDateTime instant, CustomerEntity customer, AddressEntity shippingAddress) {
        super();
        this.id = id;
        this.instant = instant;
        this.customer = customer;
        this.shippingAddress = shippingAddress;
    }
}
