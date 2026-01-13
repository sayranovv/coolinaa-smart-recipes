package com.coolinaa.repository;

import com.coolinaa.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий справочника единиц измерения.
 * Используется для проверки существования единиц (грамм, мл, шт) при создании рецептов.
 */
@Repository
public interface UnitRepository extends JpaRepository<Unit, Integer> {

    Boolean existsByName(String name);

}
