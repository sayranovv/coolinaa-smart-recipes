package com.coolinaa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "recipe_categories", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = false)
    private Set<Recipe> recipes = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = OffsetDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }

}
