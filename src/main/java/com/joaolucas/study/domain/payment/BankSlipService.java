package com.joaolucas.study.domain.payment;

import com.joaolucas.study.infrastructure.database.payment.PaymentWithSlipEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BankSlipService {

    public void addDueDate(PaymentWithSlipEntity paymentWithSlip, LocalDateTime orderDateTime) {
        paymentWithSlip.setDueDate(orderDateTime.plusMonths(7).toLocalDate());
    }
}
