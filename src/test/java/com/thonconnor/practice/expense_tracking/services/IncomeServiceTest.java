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

import com.thonconnor.practice.expense_tracking.entities.IncomeEntity;
import com.thonconnor.practice.expense_tracking.filters.TransactionFilterSpecification;
import com.thonconnor.practice.expense_tracking.mappers.IncomeMapper;
import com.thonconnor.practice.expense_tracking.models.CategoryModel;
import com.thonconnor.practice.expense_tracking.models.IncomeModel;
import com.thonconnor.practice.expense_tracking.models.requests.ReadListInput;
import com.thonconnor.practice.expense_tracking.repositories.IncomeRepository;

@ExtendWith(MockitoExtension.class)
class IncomeServiceTest {

    @Mock
    private IncomeRepository incomeRepository;
    @Mock
    private IncomeMapper incomeMapper;
    @Mock
    private TransactionFilterSpecification<IncomeEntity> specFactory;

    @InjectMocks
    private IncomeService incomeService;

    @Test
    void readIncomes_returnsMappedModels() {
        var input = new ReadListInput("101", LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 31), 0);

        Specification<IncomeEntity> ownedSpec = (root, q, cb) -> null;
        Specification<IncomeEntity> dateSpec = (root, q, cb) -> null;
        when(specFactory.ownedBy(input.userId())).thenReturn(ownedSpec);
        when(specFactory.hasTransactionDateBetween(input.startDate(), input.endDate(), IncomeEntity.class))
                .thenReturn(dateSpec);

        var entity1 = new IncomeEntity();
        entity1.setId(1L);
        var entity2 = new IncomeEntity();
        entity2.setId(2L);
        when(incomeRepository.findAll(any(Specification.class))).thenReturn(List.of(entity1, entity2));

        var model1 = IncomeModel.builder().id(1L).amount(100).userId(101L)
                .category(CategoryModel.builder().id(10L).build()).build();
        var model2 = IncomeModel.builder().id(2L).amount(200).userId(101L)
                .category(CategoryModel.builder().id(11L).build()).build();
        when(incomeMapper.map(entity1)).thenReturn(model1);
        when(incomeMapper.map(entity2)).thenReturn(model2);

        var result = incomeService.readIncomes(input);

        assertEquals(List.of(model1, model2), result);
        verify(specFactory).ownedBy(eq("101"));
        verify(specFactory).hasTransactionDateBetween(eq(input.startDate()), eq(input.endDate()), eq(IncomeEntity.class));
        verify(incomeRepository).findAll(any(Specification.class));
    }
}
