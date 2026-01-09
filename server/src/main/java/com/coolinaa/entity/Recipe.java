package com.coolinaa.entity;

import com.coolinaa.enums.RecipeStatus;
import com.coolinaa.enums.RecipeStatusConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "recipes", indexes = {
        @Index(name = "idx_recipes_user_id", columnList = "user_id"),
        @Index(name = "idx_recipes_category_id", columnList = "category_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"user", "category", "ingredients", "reviews"})
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String instructions;

    @Column(name = "preparation_time")
    private Integer preparationTime;

    @Column(name = "cooking_time")
    private Integer cookingTime;

    @Column(name = "difficulty_level")
    private Integer difficultyLevel;

    private Integer servings;

    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @Builder.Default
    @Column(name = "is_public")
    private Boolean isPublic = true;

    @Builder.Default
    @Convert(converter = RecipeStatusConverter.class)
    @Column(nullable = false, length = 20)
    private RecipeStatus status = RecipeStatus.ACTIVE;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private RecipeCategory category;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RecipeIngredient> ingredients = new HashSet<>();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Review> reviews = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = OffsetDateTime.now();
        }
        if (updatedAt == null) {
            updatedAt = OffsetDateTime.now();
        }
        if (status == null) {
            status = RecipeStatus.ACTIVE;
        }
        if (isPublic == null) {
            isPublic = true;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }


}
