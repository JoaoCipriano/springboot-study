package com.joaolucas.study.infrastructure.database.payment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.joaolucas.study.infrastructure.database.order.OrderEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class PaymentWithSlipEntity extends PaymentEntity {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dueDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime paidAt;

    public PaymentWithSlipEntity() {
    }

    public PaymentWithSlipEntity(Integer id, Integer status, OrderEntity order, LocalDate dueDate, LocalDateTime paidAt) {
        super(id, status, order);
        this.dueDate = dueDate;
        this.paidAt = paidAt;
    }
}
