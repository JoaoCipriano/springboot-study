package com.joaolucas.study.infrastructure.database.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum PaymentStatus {

    PENDING(1, "Pending"),
    PAID(2, "Paid"),
    CANCELLED(3, "Cancelled");

    private final int code;
    private final String description;

    public static PaymentStatus toEnum(Integer code) {
        return Stream.of(PaymentStatus.values())
                .filter(e -> code.equals(e.getCode()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Invalid code: " + code));
    }
}
