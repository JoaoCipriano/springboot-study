package com.joaolucas.study.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.joaolucas.study.dto.ItemPedidoDTO;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

@Getter
@Setter
@Entity
public class ItemPedido implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @EmbeddedId
    private ItemPedidoPK id = new ItemPedidoPK();

    private Double desconto;
    private Integer quantidade;
    private Double preco;

    public ItemPedido() {
    }

    public ItemPedido(Pedido pedido, Produto produto, Double desconto, Integer quantidade, Double preco) {
        super();
        id.setPedido(pedido);
        id.setProduto(produto);
        this.desconto = desconto;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    public ItemPedido(ItemPedidoDTO itemPedidoDTO) {
        this.desconto = itemPedidoDTO.desconto();
        this.quantidade = itemPedidoDTO.quantidade();
        this.preco = itemPedidoDTO.preco();
        id.setProduto(new Produto(itemPedidoDTO.produto().id(), null, null));
    }

    public double getSubTotal() {
        return (preco - desconto) * quantidade;
    }

    @JsonIgnore
    public Pedido getPedido() {
        return id.getPedido();
    }

    public void setPedido(Pedido pedido) {
        id.setPedido(pedido);
    }

    public Produto getProduto() {
        return id.getProduto();
    }

    public void setProduto(Produto produto) {
        id.setProduto(produto);
    }

    @Override
    public String toString() {
        var nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return getProduto().getNome() +
                ", Qte: " +
                getQuantidade() +
                ", Preço unitário: " +
                nf.format(getPreco()) +
                ", Subtotal: " +
                nf.format(getSubTotal()) +
                "\n";
    }
}
