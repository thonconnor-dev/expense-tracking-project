package com.thonconnor.practice.expense_tracking.mappers;

import java.time.ZoneId;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.thonconnor.practice.expense_tracking.entities.TransactionEntity;
import com.thonconnor.practice.expense_tracking.models.TransactionModel;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TransactionMapping {

    private final CategoryMapper categoryMapper;
    private final IncomeMapping incomeMapping;
    private final ExpenseMapping expenseMapping;

    public TransactionModel map(TransactionEntity transactionEntity) {
        return Optional.ofNullable(transactionEntity).map(entity -> TransactionModel.builder()
                .id(entity.getId())
                .amount(entity.getAmount())
                .description(entity.getDescription())
                .category(categoryMapper.map(entity.getCategory()))
                .userId(entity.getUser().getId())
                .transactionDate(entity.getTransactionDate() == null ? null
                        : entity.getTransactionDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .createdDate(entity.getCreatedDate() == null ? null
                        : entity.getCreatedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .income(incomeMapping.map(entity.getIncome()))
                .expense(expenseMapping.map(entity.getExpense()))
                .build())
                .orElse(null);
    }
}
