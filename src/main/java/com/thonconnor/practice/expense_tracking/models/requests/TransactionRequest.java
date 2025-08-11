package com.thonconnor.practice.expense_tracking.models.requests;

import com.thonconnor.practice.expense_tracking.entities.CategoryEntity;
import com.thonconnor.practice.expense_tracking.entities.TransactionEntity;
import com.thonconnor.practice.expense_tracking.entities.UserEntity;

public record TransactionRequest(TransactionEntity transactionEntity, UserEntity userEntity,
        CategoryEntity categoryEntity) {

}
