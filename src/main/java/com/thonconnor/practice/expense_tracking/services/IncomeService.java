package com.thonconnor.practice.expense_tracking.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.thonconnor.practice.expense_tracking.entities.IncomeEntity;
import com.thonconnor.practice.expense_tracking.filters.TransactionFilterSpecification;
import com.thonconnor.practice.expense_tracking.mappers.IncomeMapper;
import com.thonconnor.practice.expense_tracking.models.IncomeModel;
import com.thonconnor.practice.expense_tracking.models.requests.ReadListInput;
import com.thonconnor.practice.expense_tracking.repositories.IncomeRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class IncomeService {
    private final IncomeRepository incomeRepository;
    private final IncomeMapper incomeMapper;
    private final TransactionFilterSpecification<IncomeEntity> transactionFilterSpecification;

    public List<IncomeModel> readIncomes(ReadListInput readListInput) {
        log.info("read income records input={}", readListInput);
        Specification<IncomeEntity> filterSpecification = Specification.allOf(
                transactionFilterSpecification.ownedBy(readListInput.userId()),
                transactionFilterSpecification.hasTransactionDateBetween(readListInput.startDate(),
                        readListInput.endDate(), IncomeEntity.class));
        List<IncomeEntity> incomeEntities = incomeRepository.findAll(filterSpecification);
        log.info("record found={}", incomeEntities.size());
        return incomeEntities.stream().map(incomeMapper::map).collect(Collectors.toList());
    }
}
