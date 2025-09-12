package com.thonconnor.practice.expense_tracking.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thonconnor.practice.expense_tracking.models.ExpenseModel;
import com.thonconnor.practice.expense_tracking.models.ExpensesModel;
import com.thonconnor.practice.expense_tracking.models.ResponseResult;
import com.thonconnor.practice.expense_tracking.models.requests.ReadListInput;
import com.thonconnor.practice.expense_tracking.services.ExpenseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/v1")
@AllArgsConstructor
@Slf4j
@Tag(name = "Expense API")
public class ExpenseController {
        private final ExpenseService expenseService;

        @Operation(summary = "list all expenses between given date range")
        @GetMapping(path = "/expenses", produces = "application/json")
        public ResponseEntity<ResponseResult<ExpensesModel>> readExpenses(@RequestParam String userId,
                        @RequestParam @DateTimeFormat(pattern = "MM-dd-yyyy") LocalDate startDate,
                        @RequestParam @DateTimeFormat(pattern = "MM-dd-yyyy") LocalDate endDate) {
                log.info("read expense - start");
                List<ExpenseModel> expenseModels = expenseService
                                .readExpenses(new ReadListInput(userId, startDate, endDate, null, null));
                log.info("read expense - end");
                return ResponseEntity.ok()
                                .body(ResponseResult.<ExpensesModel>builder().data(new ExpensesModel(expenseModels))
                                                .build());
        }
}
