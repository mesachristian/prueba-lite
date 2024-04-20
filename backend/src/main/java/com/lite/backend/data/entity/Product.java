package com.lite.backend.data.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "productos")
public class Product {

    @Id
    @UuidGenerator
    private String code;
    private String name;
    private String characteristics;
    private List<String> prices;

    @ManyToOne
    @JoinColumn( name = "empresa_nit", nullable = false )
    private Company company;

    @ManyToOne
    @JoinColumn( name = "category_id" )
    private Category category;

    @ManyToMany
    private Set<Order> orders;
}
