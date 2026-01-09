package com.coolinaa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "recipe_ingredients", indexes = {
        @Index(name = "idx_recipe_ingredients_recipe_id", columnList = "recipe_id"),
        @Index(name = "idx_recipe_ingredients_ingredient_id", columnList = "ingredient_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"recipe", "ingredient", "unit"})
@ToString(exclude = {"recipe", "ingredient", "unit"})
public class RecipeIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(precision = 10, scale = 2)
    private BigDecimal quantity;

    @Column(length = 100)
    private String notes;

    @Column(name = "order_index")
    private Integer orderIndex = 0;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    private Unit unit;

}
