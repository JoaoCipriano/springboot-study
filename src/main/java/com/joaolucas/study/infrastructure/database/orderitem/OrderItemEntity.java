package com.joaolucas.study.infrastructure.database.orderitem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.joaolucas.study.infrastructure.database.order.OrderEntity;
import com.joaolucas.study.infrastructure.database.product.ProductEntity;
import com.joaolucas.study.domain.orderitem.OrderItem;
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
public class OrderItemEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @EmbeddedId
    private OrderItemPK id = new OrderItemPK();

    private Double discount;
    private Integer quantity;
    private Double amount;

    public OrderItemEntity() {
    }

    public OrderItemEntity(OrderEntity order, ProductEntity product, Double discount, Integer quantity, Double amount) {
        super();
        id.setOrder(order);
        id.setProduct(product);
        this.discount = discount;
        this.quantity = quantity;
        this.amount = amount;
    }

    public OrderItemEntity(OrderItem orderItem) {
        this.discount = orderItem.discount();
        this.quantity = orderItem.quantity();
        this.amount = orderItem.price();
        id.setProduct(new ProductEntity(orderItem.product().id(), null, null));
    }

    public double getSubTotal() {
        return (amount - discount) * quantity;
    }

    @JsonIgnore
    public OrderEntity getOrder() {
        return id.getOrder();
    }

    public void setOrder(OrderEntity pedido) {
        id.setOrder(pedido);
    }

    public ProductEntity getProduct() {
        return id.getProduct();
    }

    public void setProduct(ProductEntity productEntity) {
        id.setProduct(productEntity);
    }

    @Override
    public String toString() {
        var nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return getProduct().getName() +
                ", Qte: " +
                getQuantity() +
                ", Preço unitário: " +
                nf.format(getAmount()) +
                ", Subtotal: " +
                nf.format(getSubTotal()) +
                "\n";
    }
}
