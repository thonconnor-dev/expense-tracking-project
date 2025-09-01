package com.thonconnor.practice.expense_tracking.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.thonconnor.practice.expense_tracking.entities.CategoryEntity;
import com.thonconnor.practice.expense_tracking.mappers.CategoryMapper;
import com.thonconnor.practice.expense_tracking.models.CategoryModel;
import com.thonconnor.practice.expense_tracking.repositories.CategoryRepository;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void findById_returnsMappedModel_whenFound() {
        var entity = new CategoryEntity(1L, "Food", "Expense");
        var model = CategoryModel.builder().id(1L).name("Food").type("Expense").build();
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(categoryMapper.map(entity)).thenReturn(model);

        var result = categoryService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(model, result.get());
    }

    @Test
    void findById_returnsEmpty_whenNotFound() {
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        var result = categoryService.findById(99L);

        assertTrue(result.isEmpty());
    }

    @Test
    void findAll_mapsAllEntities() {
        var e1 = new CategoryEntity(1L, "Food", "Expense");
        var e2 = new CategoryEntity(2L, "Salary", "Income");
        when(categoryRepository.findAll()).thenReturn(List.of(e1, e2));

        var m1 = CategoryModel.builder().id(1L).name("Food").type("Expense").build();
        var m2 = CategoryModel.builder().id(2L).name("Salary").type("Income").build();
        when(categoryMapper.map(e1)).thenReturn(m1);
        when(categoryMapper.map(e2)).thenReturn(m2);

        var result = categoryService.findAll();

        assertEquals(List.of(m1, m2), result);
        verify(categoryRepository).findAll();
    }

    @Test
    void findByName_returnsMappedModel_whenFound() {
        var entity = new CategoryEntity(5L, "Bills", "Expense");
        var model = CategoryModel.builder().id(5L).name("Bills").type("Expense").build();
        when(categoryRepository.findByName("Bills")).thenReturn(Optional.of(entity));
        when(categoryMapper.map(entity)).thenReturn(model);

        var result = categoryService.findByName("Bills");

        assertTrue(result.isPresent());
        assertEquals(model, result.get());
    }

    @Test
    void createCategory_savesAndSetsId() {
        var request = CategoryModel.builder().name("Travel").type("Expense").build();
        var toSave = new CategoryEntity(null, "Travel", "Expense");
        var saved = new CategoryEntity(10L, "Travel", "Expense");
        when(categoryMapper.map(request)).thenReturn(toSave);
        when(categoryRepository.save(toSave)).thenReturn(saved);

        var result = categoryService.createCategory(request);

        assertEquals(10L, result.getId());
        verify(categoryRepository).save(toSave);
    }

    @Test
    void editCategory_updatesAndPersists() {
        var incoming = CategoryModel.builder().id(3L).name("Utilities").type("Expense").build();
        var existing = new CategoryEntity(3L, "Bills", "Expense");
        var updated = new CategoryEntity(3L, "Utilities", "Expense");

        when(categoryRepository.findById(3L)).thenReturn(Optional.of(existing));
        when(categoryMapper.mapEditedAttributes(existing, incoming)).thenReturn(updated);

        var result = categoryService.editCategory(incoming);

        assertEquals(incoming, result);
        verify(categoryRepository).save(updated);
    }
}
