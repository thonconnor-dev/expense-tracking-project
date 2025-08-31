package com.thonconnor.practice.expense_tracking.controllers;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thonconnor.practice.expense_tracking.models.CategoryModel;
import com.thonconnor.practice.expense_tracking.services.CategoryService;

@WebMvcTest(controllers = CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CategoryService categoryService;

    @Test
    @DisplayName("GET /v1/categories returns wrapped list of categories")
    void getAllCategories_returnsCategories() throws Exception {
        // Arrange
        var food = CategoryModel.builder().id(1L).name("Food").type("Expense").build();
        var salary = CategoryModel.builder().id(2L).name("Salary").type("Income").build();
        when(categoryService.findAll()).thenReturn(List.of(food, salary));

        // Act & Assert
        mockMvc.perform(get("/v1/categories").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.categories[0].id").value(1))
                .andExpect(jsonPath("$.data.categories[0].name").value("Food"))
                .andExpect(jsonPath("$.data.categories[0].type").value("Expense"))
                .andExpect(jsonPath("$.data.categories[1].id").value(2))
                .andExpect(jsonPath("$.data.categories[1].name").value("Salary"))
                .andExpect(jsonPath("$.data.categories[1].type").value("Income"));
    }

    @Test
    @DisplayName("POST /v1/category creates and returns category in wrapper")
    void createCategory_returnsCreatedCategory() throws Exception {
        // Arrange
        var request = CategoryModel.builder().name("Transport").type("Expense").build();
        var created = CategoryModel.builder().id(10L).name("Transport").type("Expense").build();
        when(categoryService.createCategory(request)).thenReturn(created);

        // Act & Assert
        mockMvc.perform(post("/v1/category")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(10))
                .andExpect(jsonPath("$.data.name").value("Transport"))
                .andExpect(jsonPath("$.data.type").value("Expense"));
    }

    @Test
    @DisplayName("PUT /v1/category edits and returns updated category in wrapper")
    void editCategory_returnsUpdatedCategory() throws Exception {
        // Arrange
        var request = CategoryModel.builder().id(5L).name("Bills").type("Expense").build();
        when(categoryService.editCategory(request)).thenReturn(request);

        // Act & Assert
        mockMvc.perform(put("/v1/category")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(5))
                .andExpect(jsonPath("$.data.name").value("Bills"))
                .andExpect(jsonPath("$.data.type").value("Expense"));

        verify(categoryService).editCategory(request);
    }
}
