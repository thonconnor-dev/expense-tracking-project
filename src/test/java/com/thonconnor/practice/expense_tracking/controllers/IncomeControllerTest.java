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
import com.thonconnor.practice.expense_tracking.models.IncomeModel;
import com.thonconnor.practice.expense_tracking.services.IncomeService;

@WebMvcTest(controllers = IncomeController.class)
class IncomeControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockitoBean
        private IncomeService incomeService;

        @Test
        @DisplayName("GET /v1/incomes returns wrapped list of incomes")
        void readIncomes_returnsIncomes() throws Exception {
                var inc1 = IncomeModel.builder().id(1L).amount(100).userId(5L)
                                .category(CategoryModel.builder().id(10L).build()).build();
                var inc2 = IncomeModel.builder().id(2L).amount(200).userId(5L)
                                .category(CategoryModel.builder().id(11L).build()).build();
                when(incomeService.readIncomes(org.mockito.ArgumentMatchers.any())).thenReturn(List.of(inc1, inc2));

                mockMvc.perform(get("/v1/incomes")
                                .accept(MediaType.APPLICATION_JSON)
                                .param("userId", "5")
                                .param("startDate", "06-01-2025")
                                .param("endDate", "06-30-2025"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.data.incomes[0].id").value(1))
                                .andExpect(jsonPath("$.data.incomes[0].amount").value(100.0))
                                .andExpect(jsonPath("$.data.incomes[1].id").value(2))
                                .andExpect(jsonPath("$.data.incomes[1].amount").value(200.0));
        }
}
