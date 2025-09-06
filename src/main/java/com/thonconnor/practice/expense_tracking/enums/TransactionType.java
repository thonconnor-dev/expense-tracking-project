package com.thonconnor.practice.expense_tracking.enums;

import lombok.Getter;

@Getter
public enum TransactionType {
    INCOME("Income"),
    EXPENSE("Expense");

    private String type;

    private TransactionType(String type) {
        this.type = type;
    }
}
