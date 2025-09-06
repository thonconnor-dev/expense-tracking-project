package com.thonconnor.practice.expense_tracking.mappers;

import org.springframework.stereotype.Component;

import com.thonconnor.practice.expense_tracking.enums.TransactionType;
import com.thonconnor.practice.expense_tracking.models.requests.TransactionRequest;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TransactionAssociationMapperFactory {
    private final IncomeMapper incomeMapper;
    private final ExpenseMapper expenseMapper;

    public void mapAssociation(TransactionRequest transactionRequest) {
        if (transactionRequest.transactionEntity().getType().equalsIgnoreCase(TransactionType.INCOME.getType())) {
            transactionRequest.transactionEntity().setIncome(incomeMapper.mapNewIncome(transactionRequest));
        } else {
            transactionRequest.transactionEntity().setExpense(expenseMapper.mapNewExpense(transactionRequest));
        }
    }
}
