package com.coolinaa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Сущность "Единица измерения".
 * <p>
 * Справочник мер веса и объема (кг, г, л, мл, шт, ст. ложка).
 * Используется как в рецептах (для указания необходимого количества), так и в "холодильнике" пользователя.
 * Флаг isMetric позволяет разделять метрические и нестандартные меры.
 * </p>
 */
@Entity
@Table(name = "units")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(length = 10)
    private String abbreviation;

    @Column(name = "is_metric")
    private Boolean isMetric = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL, orphanRemoval = false)
    private Set<RecipeIngredient> recipeIngredients = new HashSet<>();

    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL, orphanRemoval = false)
    private Set<UserIngredient> userIngredients = new HashSet<>();

    @PrePersist
    public void onCreate() {
        if (createdAt == null) {
            createdAt = OffsetDateTime.now();
        }
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }

}
