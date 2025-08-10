package com.thonconnor.practice.expense_tracking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.thonconnor.practice.expense_tracking.entities.CategoryEntity;
import com.thonconnor.practice.expense_tracking.mappers.CategoryMapper;
import com.thonconnor.practice.expense_tracking.models.CategoryModel;

@ExtendWith(MockitoExtension.class)
public class CategoryMapperTest {

    @InjectMocks
    private CategoryMapper categoryMapper;

    @Test
    public void testMappingModelToEntity() {
        CategoryModel categoryModel = CategoryModel.builder()
                .id(1L)
                .name("Food")
                .type("Expense")
                .build();
        CategoryEntity categoryEntity = categoryMapper.map(categoryModel);
        assertNull(categoryEntity.getId(), "ID should be null for new entities");
        assertEquals("Food", categoryEntity.getName());
        assertEquals("Expense", categoryEntity.getType());
    }

    @Test
    public void testMappingEntityToModel() {
        CategoryEntity categoryEntity = new CategoryEntity(1L, "Food", "Expense");
        CategoryModel categoryModel = categoryMapper.map(categoryEntity);
        assertEquals(1L, categoryModel.getId());
        assertEquals("Food", categoryModel.getName());
        assertEquals("Expense", categoryModel.getType());
    }
}
