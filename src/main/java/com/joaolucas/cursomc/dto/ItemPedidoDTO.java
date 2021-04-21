package com.joaolucas.cursomc.dto;

import java.io.Serializable;

public class ItemPedidoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Double desconto;
    private Integer quantidade;
    private Double preco;
    private ProdutoDTO produto;

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public ProdutoDTO getProduto() {
        return produto;
    }

    public void setProduto(ProdutoDTO produto) {
        this.produto = produto;
    }
}
