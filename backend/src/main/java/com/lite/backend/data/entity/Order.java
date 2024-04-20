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
@Table(name = "ordenes")
public class Order {

    @Id
    @UuidGenerator
    private String id;

    private String name;

    @ManyToOne
    @JoinColumn( name = "client_id" )
    private Client client;

    @ManyToMany
    private Set<Product> products;
}
