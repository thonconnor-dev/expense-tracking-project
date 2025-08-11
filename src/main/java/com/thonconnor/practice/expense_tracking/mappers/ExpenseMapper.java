package com.thonconnor.practice.expense_tracking.mappers;

import java.time.ZoneId;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.thonconnor.practice.expense_tracking.entities.ExpenseEntity;
import com.thonconnor.practice.expense_tracking.models.ExpenseModel;
import com.thonconnor.practice.expense_tracking.models.requests.TransactionRequest;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ExpenseMapper {
        private final CategoryMapper categoryMapper;

        public ExpenseModel map(ExpenseEntity expenseEntity) {
                return Optional.ofNullable(expenseEntity)
                                .map(entity -> ExpenseModel.builder()
                                                .id(expenseEntity.getId())
                                                .amount(expenseEntity.getAmount())
                                                .expenseDate(expenseEntity.getExpenseDate() == null ? null
                                                                : expenseEntity.getExpenseDate().toInstant()
                                                                                .atZone(ZoneId.systemDefault())
                                                                                .toLocalDate())
                                                .createdDate(expenseEntity.getCreatedDate() == null ? null
                                                                : expenseEntity.getCreatedDate().toInstant()
                                                                                .atZone(ZoneId.systemDefault())
                                                                                .toLocalDate())
                                                .category(categoryMapper.map(expenseEntity.getCategory()))
                                                .userId(expenseEntity.getUser().getId())
                                                .description(expenseEntity.getDescription())
                                                .build())
                                .orElse(null);
        }

        public ExpenseEntity mapNewExpense(TransactionRequest transactionRequest) {
                return new ExpenseEntity(null, transactionRequest.userEntity(), transactionRequest.categoryEntity(),
                                transactionRequest.transactionEntity().getAmount(),
                                transactionRequest.transactionEntity().getDescription(), null,
                                transactionRequest.transactionEntity().getTransactionDate());
        }

}
