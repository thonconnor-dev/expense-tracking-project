package com.thonconnor.practice.expense_tracking.models.requests;

import java.time.LocalDate;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record TransactionInput(
        @NotBlank(message = "description cannot be blank") String description,
        @Min(value = 0, message = "amount is at least zero") double amount,
        @Pattern(regexp = "(?i)income|expense", message = "type can be Income or Expense") String type,
        @NotNull Long categoryId,
        @Valid @NotNull UserInput user,
        @NotNull LocalDate transactionDate) {

}
