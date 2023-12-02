package com.joaolucas.study.infrastructure.database.order;

import com.joaolucas.study.infrastructure.database.address.AddressEntity;
import com.joaolucas.study.infrastructure.database.customer.CustomerEntity;
import com.joaolucas.study.infrastructure.database.orderitem.OrderItemEntity;
import com.joaolucas.study.infrastructure.database.payment.PaymentEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Getter
@Setter
@Entity
public class OrderEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Date instant;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "order")
    private PaymentEntity payment;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @ManyToOne
    @JoinColumn(name = "shipping_address_id")
    private AddressEntity shippingAddress;

    @OneToMany(mappedBy = "id.order")
    private Set<OrderItemEntity> itens = new HashSet<>();

    public OrderEntity() {
    }

    public OrderEntity(Integer id, Date instant, CustomerEntity customer, AddressEntity shippingAddress) {
        super();
        this.id = id;
        this.instant = instant;
        this.customer = customer;
        this.shippingAddress = shippingAddress;
    }

    public double getValorTotal() {
        var soma = 0.0;
        for (OrderItemEntity ip : itens) {
            soma += ip.getSubTotal();
        }
        return soma;
    }

    @Override
    public String toString() {
        var nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        var sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        final var sb = new StringBuilder();
        sb.append("Pedido número: ");
        sb.append(getId());
        sb.append(", Instante: ");
        sb.append(sdf.format(getInstant()));
        sb.append(", Cliente: ");
        sb.append(getCustomer().getEmail());
        sb.append(", Situação do pagamento: ");
        sb.append(getPayment().getState().getDescription());
        sb.append("\nDetalhes:\n");
        for (OrderItemEntity ip : getItens()) {
            sb.append(ip.toString());
        }
        sb.append("Valor total: ");
        sb.append(nf.format(getValorTotal()));
        return sb.toString();
    }
}
