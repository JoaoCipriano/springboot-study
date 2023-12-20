package com.joaolucas.study.infrastructure.database.payment;

import com.joaolucas.study.infrastructure.database.order.OrderEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
@Entity
public class PaymentWithCardEntity extends PaymentEntity {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer installmentsNumber;

    public PaymentWithCardEntity() {
    }

    public PaymentWithCardEntity(Integer id, Integer status, OrderEntity order, Integer installmentsNumber) {
        super(id, status, order);
        this.installmentsNumber = installmentsNumber;
    }
}
