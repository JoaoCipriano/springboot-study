package com.joaolucas.study.controller.order.model;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record PaymentResponse(PaymentStatus status,
                              Integer installmentsNumber,
                              LocalDate dueDate,
                              LocalDateTime paidAt) {
}
