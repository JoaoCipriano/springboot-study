package com.joaolucas.study.infrastructure.database.payment;

import com.joaolucas.study.infrastructure.database.order.OrderEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class PaymentEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;

    private Integer status;

    @OneToOne
    @JoinColumn(name = "order_id")
    @MapsId
    private OrderEntity order;

    protected PaymentEntity() {
    }

    protected PaymentEntity(Integer id, Integer status, OrderEntity order) {
        super();
        this.id = id;
        this.status = status;
        this.order = order;
    }
}
