package com.joaolucas.study.infrastructure.database.orderitem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.joaolucas.study.infrastructure.database.order.OrderEntity;
import com.joaolucas.study.infrastructure.database.product.ProductEntity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
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

    private BigDecimal discount;
    private Integer quantity;
    private BigDecimal amount;

    public OrderItemEntity() {
    }

    public OrderItemEntity(OrderEntity order, ProductEntity product, BigDecimal discount, Integer quantity, BigDecimal amount) {
        super();
        id.setOrder(order);
        id.setProduct(product);
        this.discount = discount;
        this.quantity = quantity;
        this.amount = amount;
    }

    public BigDecimal getSubTotal() {
        return amount.subtract(discount).multiply(BigDecimal.valueOf(quantity));
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
