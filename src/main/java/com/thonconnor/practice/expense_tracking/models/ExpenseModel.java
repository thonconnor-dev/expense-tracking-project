package com.thonconnor.practice.expense_tracking.models;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class ExpenseModel {
    private long id;
    private double amount;
    private String userId;
    private CategoryModel category;
    private String description;
    private LocalDate expenseDate;
    private LocalDate createdDate;
}
