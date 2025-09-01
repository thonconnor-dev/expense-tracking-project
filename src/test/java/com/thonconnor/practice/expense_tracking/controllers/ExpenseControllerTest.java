package com.thonconnor.practice.expense_tracking.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.thonconnor.practice.expense_tracking.models.CategoryModel;
import com.thonconnor.practice.expense_tracking.models.ExpenseModel;
import com.thonconnor.practice.expense_tracking.services.ExpenseService;

@WebMvcTest(controllers = ExpenseController.class)
class ExpenseControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockitoBean
        private ExpenseService expenseService;

        @Test
        @DisplayName("GET /v1/expenses returns wrapped list of expenses")
        void readExpenses_returnsExpenses() throws Exception {
                var ex1 = ExpenseModel.builder().id(1L).amount(30).userId(5L)
                                .category(CategoryModel.builder().id(10L).build()).build();
                var ex2 = ExpenseModel.builder().id(2L).amount(45).userId(5L)
                                .category(CategoryModel.builder().id(11L).build()).build();
                when(expenseService.readExpenses(org.mockito.ArgumentMatchers.any())).thenReturn(List.of(ex1, ex2));

                mockMvc.perform(get("/v1/expenses")
                                .accept(MediaType.APPLICATION_JSON)
                                .param("userId", "5")
                                .param("startDate", "06-01-2025")
                                .param("endDate", "06-30-2025"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.data.expenses[0].id").value(1))
                                .andExpect(jsonPath("$.data.expenses[0].amount").value(30.0))
                                .andExpect(jsonPath("$.data.expenses[1].id").value(2))
                                .andExpect(jsonPath("$.data.expenses[1].amount").value(45.0));
        }
}
