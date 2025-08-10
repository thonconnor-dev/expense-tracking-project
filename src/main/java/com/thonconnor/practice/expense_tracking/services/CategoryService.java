package com.thonconnor.practice.expense_tracking.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import com.thonconnor.practice.expense_tracking.mappers.CategoryMapper;
import com.thonconnor.practice.expense_tracking.models.CategoryModel;
import com.thonconnor.practice.expense_tracking.repositories.CategoryRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service for managing categories of transactions.
 */
@Service
@AllArgsConstructor
@Slf4j
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    /**
     * Find a category by its ID.
     * 
     * @param id category ID
     * @return Optional containing the CategoryModel if found, otherwise empty
     */
    public Optional<CategoryModel> findById(Long id) {
        log.info("find category {}", id);
        return categoryRepository.findById(id)
                .map(categoryMapper::map);
    }

    /**
     * Find all categories.
     * 
     * @return List of CategoryModel representing all categories
     */
    public List<CategoryModel> findAll() {
        log.info("find all categories");
        return Streamable.of(categoryRepository.findAll())
                .map(categoryMapper::map)
                .toList();

    }

    /**
     * Find a category by its name.
     * 
     * @param name category name
     * @return Optional containing the CategoryModel if found, otherwise empty
     */

    public Optional<CategoryModel> findByName(String name) {
        log.info("find category by name {}", name);
        return categoryRepository.findByName(name)
                .map(categoryMapper::map);
    }
}
