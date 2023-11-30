package com.joaolucas.study.infrastructure.database.payment;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.joaolucas.study.infrastructure.database.order.OrderEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
@Entity
@JsonTypeName("paymentWithCard")
public class PaymentWithCardEntity extends PaymentEntity {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer installmentsNumber;

    public PaymentWithCardEntity() {
    }

    public PaymentWithCardEntity(Integer id, PaymentStatus paymentStatus, OrderEntity order, Integer installmentsNumber) {
        super(id, paymentStatus, order);
        this.installmentsNumber = installmentsNumber;
    }
}
