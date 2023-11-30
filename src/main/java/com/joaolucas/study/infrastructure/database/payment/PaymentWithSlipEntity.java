package com.joaolucas.study.infrastructure.database.payment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.joaolucas.study.infrastructure.database.order.OrderEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.util.Date;

@Getter
@Setter
@Entity
@JsonTypeName("paymentWithSlip")
public class PaymentWithSlipEntity extends PaymentEntity {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dueDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date paymentDate;

    public PaymentWithSlipEntity() {
    }

    public PaymentWithSlipEntity(Integer id, PaymentStatus paymentStatus, OrderEntity order, Date dueDate, Date paymentDate) {
        super(id, paymentStatus, order);
        this.dueDate = dueDate;
        this.paymentDate = paymentDate;
    }
}
