package com.thonconnor.practice.expense_tracking.models;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class TransactionModel {
    private long id;
    private double amount;
    private long userId;
    private CategoryModel category;
    private String description;
    private String type;
    private LocalDate transactionDate;
    private LocalDate createdDate;

}
