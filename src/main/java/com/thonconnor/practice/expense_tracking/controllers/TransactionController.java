package com.thonconnor.practice.expense_tracking.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.thonconnor.practice.expense_tracking.mappers.TransactionMapper;
import com.thonconnor.practice.expense_tracking.models.ResponseResult;
import com.thonconnor.practice.expense_tracking.models.TransactionModel;
import com.thonconnor.practice.expense_tracking.models.TransactionsModel;
import com.thonconnor.practice.expense_tracking.models.requests.ReadListInput;
import com.thonconnor.practice.expense_tracking.models.requests.TransactionInput;
import com.thonconnor.practice.expense_tracking.services.TransactionService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/v1")
/**
 * Transaction endpoints (v1). Currently supports create.
 */
public class TransactionController {
    // Business logic for transactions
    private TransactionService transactionService;
    // Maps request models to domain models
    private TransactionMapper transactionMapper;

    /**
     * Create a new transaction.
     * 
     * @param input request body
     * @return created transaction wrapped in ResponseResult
     */
    @Operation(summary = "create new transaction")
    @PostMapping(path = "/transaction", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ResponseResult<TransactionModel>> createTransaction(
            @Valid @RequestBody TransactionInput input) {
        log.info("received request to create new transaction");
        TransactionModel response = transactionService.createTransaction(transactionMapper.map(input));
        log.info("transaction created successfully id={}", response.getId());
        return ResponseEntity.ok().body(ResponseResult.<TransactionModel>builder().data(response).build());
    }

    @Operation(summary = "list all transactions between given date range")
    @GetMapping(path = "/transactions", produces = "application/json")
    public ResponseEntity<ResponseResult<TransactionsModel>> readTransactions(@RequestParam String userId,
            @RequestParam @DateTimeFormat(pattern = "MM-dd-yyyy") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "MM-dd-yyyy") LocalDate endDate) {
        log.info("read transactions - start");
        List<TransactionModel> transactionModels = transactionService
                .readTransactionList(new ReadListInput(userId, startDate, endDate, null, null));
        log.info("read transactions - end");
        return ResponseEntity.ok()
                .body(ResponseResult.<TransactionsModel>builder().data(new TransactionsModel(transactionModels))
                        .build());
    }

}
