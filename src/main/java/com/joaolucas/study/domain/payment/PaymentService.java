package com.joaolucas.study.domain.payment;

import com.joaolucas.study.infrastructure.database.payment.PaymentEntity;
import com.joaolucas.study.infrastructure.database.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repo;

    public void save(PaymentEntity paymentEntity) {
        repo.save(paymentEntity);
    }
}
