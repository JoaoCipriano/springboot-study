package com.joaolucas.study.controller.order.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PaymentRequest(PaymentStatus status,
                             Integer installmentsNumber,
                             LocalDate dueDate,
                             LocalDateTime paidAt) {
}
