package com.thonconnor.practice.expense_tracking.models.requests;

import java.time.LocalDate;

public record TransactionInput(String description, double amount, String type, long categoryId, UserInput user,
        LocalDate transactionDate) {

}
