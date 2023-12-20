package com.joaolucas.study.infrastructure.database.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.joaolucas.study.infrastructure.database.category.CategoryEntity;
import com.joaolucas.study.infrastructure.database.orderitem.OrderItemEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class ProductEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private BigDecimal price;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "PRODUCT_CATEGORY",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<CategoryEntity> categories = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "id.product")
    private Set<OrderItemEntity> items = new HashSet<>();

    public ProductEntity() {
    }

    public ProductEntity(Integer id, String name, BigDecimal price) {
        super();
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
