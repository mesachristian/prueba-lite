package com.lite.backend.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categoria")
public class Category {

    @Id
    @UuidGenerator
    private String id;

    private String name;

    @OneToMany(mappedBy = "category")
    private Set<Product> products;
}
