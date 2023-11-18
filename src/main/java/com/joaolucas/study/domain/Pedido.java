package com.joaolucas.study.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
public class Pedido implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Date instante;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "pedido")
    private Pagamento pagamento;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "endereco_de_entrega_id")
    private Endereco enderecoDeEntrega;

    @OneToMany(mappedBy = "id.pedido")
    private Set<ItemPedido> itens = new HashSet<>();

    public Pedido() {
    }

    public Pedido(Integer id, Date instante, Cliente cliente, Endereco enderecoDeEntrega) {
        super();
        this.id = id;
        this.instante = instante;
        this.cliente = cliente;
        this.enderecoDeEntrega = enderecoDeEntrega;
    }

    public double getValorTotal() {
        var soma = 0.0;
        for (ItemPedido ip : itens) {
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
        sb.append(sdf.format(getInstante()));
        sb.append(", Cliente: ");
        sb.append(getCliente().getFirstName());
        sb.append(" ");
        sb.append(getCliente().getLastName());
        sb.append(", Situação do pagamento: ");
        sb.append(getPagamento().getEstado().getDescricao());
        sb.append("\nDetalhes:\n");
        for (ItemPedido ip : getItens()) {
            sb.append(ip.toString());
        }
        sb.append("Valor total: ");
        sb.append(nf.format(getValorTotal()));
        return sb.toString();
    }
}
