package com.joaolucas.study.dto;

import com.joaolucas.study.domain.Pagamento;

import java.util.Set;

public record PedidoNewDTO(
        ClienteDTO cliente,
        EnderecoDTO enderecoDeEntrega,
        Pagamento pagamento,
        Set<ItemPedidoDTO> itens) {
}
