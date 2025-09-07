package com.thonconnor.practice.expense_tracking.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thonconnor.practice.expense_tracking.mappers.TransactionMapper;
import com.thonconnor.practice.expense_tracking.models.CategoryModel;
import com.thonconnor.practice.expense_tracking.models.TransactionModel;
import com.thonconnor.practice.expense_tracking.models.requests.TransactionInput;
import com.thonconnor.practice.expense_tracking.models.requests.UserInput;
import com.thonconnor.practice.expense_tracking.services.TransactionService;

@WebMvcTest(controllers = TransactionController.class)
class TransactionControllerTest {

        @Autowired
        private MockMvc mockMvc;
        @Autowired
        private ObjectMapper objectMapper;

        @MockBean
        private TransactionService transactionService;
        @MockBean
        private TransactionMapper transactionMapper;

        @Test
        @DisplayName("POST /v1/transaction creates and returns transaction in wrapper")
        void createTransaction_returnsCreatedTransaction() throws Exception {
                var input = new TransactionInput("Coffee", 4.5, "EXPENSE", 3L, new UserInput(101L),
                                java.time.LocalDate.of(2025, 6, 15));
                var modelFromInput = TransactionModel.builder().amount(4.5).userId(101L)
                                .category(CategoryModel.builder().id(3L).build()).type("EXPENSE").build();
                var created = TransactionModel.builder().id(55L).amount(4.5).userId(101L)
                                .category(CategoryModel.builder().id(3L).build()).type("EXPENSE").build();

                when(transactionMapper.map(input)).thenReturn(modelFromInput);
                when(transactionService.createTransaction(modelFromInput)).thenReturn(created);

                mockMvc.perform(post("/v1/transaction")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(input)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.data.id").value(55))
                                .andExpect(jsonPath("$.data.amount").value(4.5))
                                .andExpect(jsonPath("$.data.type").value("EXPENSE"));

                verify(transactionMapper).map(input);
                verify(transactionService).createTransaction(modelFromInput);
        }

        @Test
        @DisplayName("GET /v1/transactions returns wrapped list of transactions")
        void readTransactions_returnsTransactions() throws Exception {
                var t1 = TransactionModel.builder().id(1L).amount(10).userId(5L).build();
                var t2 = TransactionModel.builder().id(2L).amount(20).userId(5L).build();
                when(transactionService.readTransactionList(org.mockito.ArgumentMatchers.any()))
                                .thenReturn(List.of(t1, t2));

                mockMvc.perform(get("/v1/transactions")
                                .accept(MediaType.APPLICATION_JSON)
                                .param("userId", "5")
                                .param("startDate", "06-01-2025")
                                .param("endDate", "06-30-2025"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.data.transactions[0].id").value(1))
                                .andExpect(jsonPath("$.data.transactions[1].id").value(2));
        }
}
