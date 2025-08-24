package com.thonconnor.practice.expense_tracking.mappers;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.thonconnor.practice.expense_tracking.entities.CategoryEntity;
import com.thonconnor.practice.expense_tracking.models.CategoryModel;

@Component
public class CategoryMapper {

    /**
     * Maps a CategoryEntity to a CategoryModel.
     * 
     * @param categoryEntity
     * @return CategoryModel
     */
    public CategoryModel map(CategoryEntity categoryEntity) {
        return Optional.ofNullable(categoryEntity).map(entity -> CategoryModel.builder()
                .id(categoryEntity.getId())
                .name(categoryEntity.getName())
                .type(categoryEntity.getType())
                .build())
                .orElse(null);
    }

    /**
     * Maps a CategoryModel to a CategoryEntity.
     * 
     * @param categoryModel
     * @return CategoryEntity
     */
    public CategoryEntity map(CategoryModel categoryModel) {
        return Optional.ofNullable(categoryModel)
                .map(model -> new CategoryEntity(null, categoryModel.getName(), categoryModel.getType()))
                .orElse(null);
    }

    /**
     * map updated fields of category from model to entity
     * 
     * @param categoryEntity
     * @param categoryModel
     * @return instance of category entity with new updated value
     */
    public CategoryEntity mapEditedAttributes(CategoryEntity categoryEntity, CategoryModel categoryModel) {
        categoryEntity.setName(categoryModel.getName());
        categoryEntity.setType(categoryModel.getType());
        return categoryEntity;
    }

}
