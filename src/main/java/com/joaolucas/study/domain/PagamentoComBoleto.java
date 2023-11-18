package com.joaolucas.study.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.joaolucas.study.domain.enums.EstadoPagamento;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.util.Date;

@Getter
@Setter
@Entity
@JsonTypeName("pagamentoComBoleto")
public class PagamentoComBoleto extends Pagamento {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataVencimento;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataPagamento;

    public PagamentoComBoleto() {
    }

    public PagamentoComBoleto(Integer id, EstadoPagamento estado, Pedido pedido, Date dataVencimento, Date dataPagamento) {
        super(id, estado, pedido);
        this.dataVencimento = dataVencimento;
        this.dataPagamento = dataPagamento;
    }
}
