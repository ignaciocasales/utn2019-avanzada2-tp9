package com.utn2019.avanzada2.tp9.domain;

import static javax.persistence.GenerationType.IDENTITY;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer productId;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
}
