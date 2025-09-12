package com.thonconnor.practice.expense_tracking.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.thonconnor.practice.expense_tracking.entities.ExpenseEntity;
import com.thonconnor.practice.expense_tracking.filters.TransactionFilterSpecification;
import com.thonconnor.practice.expense_tracking.mappers.ExpenseMapper;
import com.thonconnor.practice.expense_tracking.models.ExpenseModel;
import com.thonconnor.practice.expense_tracking.models.requests.ReadListInput;
import com.thonconnor.practice.expense_tracking.repositories.ExpenseRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;
    private final TransactionFilterSpecification<ExpenseEntity> transactionFilterSpecification;

    public List<ExpenseModel> readExpenses(ReadListInput readListInput) {
        log.info("read expense records input={}", readListInput);
        Specification<ExpenseEntity> filterSpecification =
                Specification.allOf(transactionFilterSpecification.ownedBy(readListInput.userId()),
                        transactionFilterSpecification.hasTransactionDateBetween(
                                readListInput.startDate(), readListInput.endDate(),
                                ExpenseEntity.class));
        List<ExpenseEntity> incomeEntities = expenseRepository.findAll(filterSpecification);
        log.info("record found={}", incomeEntities.size());
        return incomeEntities.stream().map(expenseMapper::map).collect(Collectors.toList());
    }

}
