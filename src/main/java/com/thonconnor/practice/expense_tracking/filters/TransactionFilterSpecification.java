package com.thonconnor.practice.expense_tracking.filters;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.thonconnor.practice.expense_tracking.entities.IncomeEntity;
import com.thonconnor.practice.expense_tracking.entities.TransactionEntity;

@Component
public class TransactionFilterSpecification<T> {

    public Specification<T> ownedBy(String userId) {
        return (root, query, cb) -> {
            return userId == null ? null : cb.equal(root.get("user").get("id"), userId);
        };
    }

    public Specification<T> hasTransactionDateBetween(LocalDate startDate, LocalDate endDate, Class<T> classType) {
        return (root, query, cb) -> {
            String dateAttributeName = "expenseDate";
            if (classType == IncomeEntity.class) {
                dateAttributeName = "incomeDate";
            } else if (classType == TransactionEntity.class) {
                dateAttributeName = "transactionDate";
            }
            return startDate == null || endDate == null ? null
                    : cb.between(root.get(dateAttributeName),
                            Timestamp.valueOf(startDate.atStartOfDay()),
                            Timestamp.valueOf(endDate.atTime(LocalTime.MAX)));
        };
    }
}
