package com.thonconnor.practice.expense_tracking.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import com.thonconnor.practice.expense_tracking.entities.ExpenseEntity;
import com.thonconnor.practice.expense_tracking.filters.TransactionFilterSpecification;
import com.thonconnor.practice.expense_tracking.mappers.ExpenseMapper;
import com.thonconnor.practice.expense_tracking.models.CategoryModel;
import com.thonconnor.practice.expense_tracking.models.ExpenseModel;
import com.thonconnor.practice.expense_tracking.models.requests.ReadListInput;
import com.thonconnor.practice.expense_tracking.repositories.ExpenseRepository;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;
    @Mock
    private ExpenseMapper expenseMapper;
    @Mock
    private TransactionFilterSpecification<ExpenseEntity> specFactory;

    @InjectMocks
    private ExpenseService expenseService;

    @Test
    void readExpenses_returnsMappedModels() {
        var input = new ReadListInput("101", LocalDate.of(2025, 2, 1), LocalDate.of(2025, 2, 28), 0);

        Specification<ExpenseEntity> ownedSpec = (root, q, cb) -> null;
        Specification<ExpenseEntity> dateSpec = (root, q, cb) -> null;
        when(specFactory.ownedBy(input.userId())).thenReturn(ownedSpec);
        when(specFactory.hasTransactionDateBetween(input.startDate(), input.endDate(), ExpenseEntity.class))
                .thenReturn(dateSpec);

        var entity1 = new ExpenseEntity();
        entity1.setId(1L);
        var entity2 = new ExpenseEntity();
        entity2.setId(2L);
        when(expenseRepository.findAll(any(Specification.class))).thenReturn(List.of(entity1, entity2));

        var model1 = ExpenseModel.builder().id(1L).amount(50).userId(101L)
                .category(CategoryModel.builder().id(20L).build()).build();
        var model2 = ExpenseModel.builder().id(2L).amount(75).userId(101L)
                .category(CategoryModel.builder().id(21L).build()).build();
        when(expenseMapper.map(entity1)).thenReturn(model1);
        when(expenseMapper.map(entity2)).thenReturn(model2);

        var result = expenseService.readExpenses(input);

        assertEquals(List.of(model1, model2), result);
        verify(specFactory).ownedBy(eq("101"));
        verify(specFactory).hasTransactionDateBetween(eq(input.startDate()), eq(input.endDate()), eq(ExpenseEntity.class));
        verify(expenseRepository).findAll(any(Specification.class));
    }
}
