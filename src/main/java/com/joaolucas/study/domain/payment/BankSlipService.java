package com.joaolucas.study.domain.payment;

import com.joaolucas.study.infrastructure.database.payment.PaymentWithSlipEntity;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class BankSlipService {

    public void preencherPagamentoComBoleto(PaymentWithSlipEntity pagto, Date instanteDoPedido) {
        var cal = Calendar.getInstance();
        cal.setTime(instanteDoPedido);
        cal.add(Calendar.DAY_OF_MONTH, 7);
        pagto.setDueDate(cal.getTime());
    }
}
