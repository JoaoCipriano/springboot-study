package com.joaolucas.study.dto;

public record ItemPedidoDTO(
        Double desconto,
        Integer quantidade,
        Double preco,
        ProdutoDTO produto) {
}
