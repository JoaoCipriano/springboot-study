package com.joaolucas.cursomc.dto;

import com.joaolucas.cursomc.domain.Pagamento;

import java.io.Serializable;
import java.util.Set;

public class PedidoNewDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private ClienteDTO cliente;
    private EnderecoDTO enderecoDeEntrega;
    private Pagamento pagamento;
    private Set<ItemPedidoDTO> itens;

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    public EnderecoDTO getEnderecoDeEntrega() {
        return enderecoDeEntrega;
    }

    public void setEnderecoDeEntrega(EnderecoDTO enderecoDeEntrega) {
        this.enderecoDeEntrega = enderecoDeEntrega;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public Set<ItemPedidoDTO> getItens() {
        return itens;
    }

    public void setItens(Set<ItemPedidoDTO> itens) {
        this.itens = itens;
    }
}
