package com.thonconnor.practice.expense_tracking.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.thonconnor.practice.expense_tracking.mappers.TransactionMapper;
import com.thonconnor.practice.expense_tracking.models.ResponseResult;
import com.thonconnor.practice.expense_tracking.models.TransactionModel;
import com.thonconnor.practice.expense_tracking.models.requests.TransactionInput;
import com.thonconnor.practice.expense_tracking.services.TransactionService;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public ResponseEntity<ResponseResult<TransactionModel>> createTransaction(@RequestBody TransactionInput input) {
        // TODO missing validation on input
        log.info("received request to create new transaction");
        TransactionModel response = transactionService.createTransaction(transactionMapper.map(input));
        log.info("transaction created successfully id={}", response.getId());
        return ResponseEntity.ok().body(ResponseResult.<TransactionModel>builder().data(response).build());
    }

}
