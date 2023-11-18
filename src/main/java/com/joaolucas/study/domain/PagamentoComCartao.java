package com.joaolucas.study.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.joaolucas.study.domain.enums.EstadoPagamento;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
@Entity
@JsonTypeName("pagamentoComCartao")
public class PagamentoComCartao extends Pagamento {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer numeroDeParcelas;

    public PagamentoComCartao() {
    }

    public PagamentoComCartao(Integer id, EstadoPagamento estado, Pedido pedido, Integer numeroDeParcelas) {
        super(id, estado, pedido);
        this.numeroDeParcelas = numeroDeParcelas;
    }
}
