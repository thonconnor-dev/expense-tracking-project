package com.thonconnor.practice.expense_tracking.mappers;

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
        return CategoryModel.builder()
                .id(categoryEntity.getId())
                .name(categoryEntity.getName())
                .type(categoryEntity.getType())
                .build();
    }

    /**
     * Maps a CategoryModel to a CategoryEntity.
     * 
     * @param categoryModel
     * @return CategoryEntity
     */
    public CategoryEntity map(CategoryModel categoryModel) {
        return new CategoryEntity(null, categoryModel.getName(), categoryModel.getType());
    }

}
