package com.thonconnor.practice.expense_tracking.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import com.thonconnor.practice.expense_tracking.entities.CategoryEntity;
import com.thonconnor.practice.expense_tracking.exceptions.TrackingCustomException;
import com.thonconnor.practice.expense_tracking.mappers.CategoryMapper;
import com.thonconnor.practice.expense_tracking.models.CategoryModel;
import com.thonconnor.practice.expense_tracking.models.ErrorDetail;
import com.thonconnor.practice.expense_tracking.repositories.CategoryRepository;

import jakarta.transaction.Transactional;
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

    /**
     * create new category record
     * 
     * @param categoryModel
     * @return category model with new record id
     */
    @Transactional
    public CategoryModel createCategory(CategoryModel categoryModel) {
        log.info("new category input={}", categoryModel.toString());
        CategoryEntity categoryEntity = categoryRepository.save(categoryMapper.map(categoryModel));
        log.info("new category id={}", categoryEntity.getId());
        categoryModel.setId(categoryEntity.getId());
        return categoryModel;
    }

    /**
     * edit existing category record
     * 
     * @param categoryModel
     * @return category model if updated successfully
     */
    @Transactional
    public CategoryModel editCategory(CategoryModel categoryModel) {
        log.info("edit category input={}", categoryModel.toString());
        Optional<CategoryEntity> categoryEntityOpt = categoryRepository.findById(categoryModel.getId());
        if (!categoryEntityOpt.isPresent()) {
            log.error("missing category record id={}", categoryModel.getId());
            throw new TrackingCustomException(ErrorDetail.CATEGORY_NOT_FOUND);
        }

        CategoryEntity updatedCategoryEntity = categoryMapper.mapEditedAttributes(categoryEntityOpt.get(),
                categoryModel);
        categoryRepository.save(updatedCategoryEntity);
        return categoryModel;
    }
}
