package com.thonconnor.practice.expense_tracking.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import com.thonconnor.practice.expense_tracking.entities.CategoryEntity;
import com.thonconnor.practice.expense_tracking.entities.TransactionEntity;
import com.thonconnor.practice.expense_tracking.entities.UserEntity;
import com.thonconnor.practice.expense_tracking.filters.TransactionFilterSpecification;
import com.thonconnor.practice.expense_tracking.mappers.TransactionMapper;
import com.thonconnor.practice.expense_tracking.models.CategoryModel;
import com.thonconnor.practice.expense_tracking.models.TransactionModel;
import com.thonconnor.practice.expense_tracking.models.requests.ReadListInput;
import com.thonconnor.practice.expense_tracking.repositories.CategoryRepository;
import com.thonconnor.practice.expense_tracking.repositories.TransactionRepository;
import com.thonconnor.practice.expense_tracking.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionMapper transactionMapper;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private TransactionFilterSpecification<TransactionEntity> specFactory;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void createTransaction_setsIdFromSavedEntity() {
        var model = TransactionModel.builder()
                .amount(123.45)
                .userId(7L)
                .category(CategoryModel.builder().id(3L).build())
                .type("EXPENSE")
                .build();

        var category = new CategoryEntity(3L, "Food", "Expense");
        var user = new UserEntity(7L, "jane", null);
        when(categoryRepository.findById(3L)).thenReturn(Optional.of(category));
        when(userRepository.findById(7L)).thenReturn(Optional.of(user));

        var toSave = new TransactionEntity();
        when(transactionMapper.mapNewEntity(model, user, category)).thenReturn(toSave);

        var saved = new TransactionEntity();
        saved.setId(99L);
        when(transactionRepository.saveAndFlush(toSave)).thenReturn(saved);

        var result = transactionService.createTransaction(model);

        assertEquals(99L, result.getId());
        verify(transactionRepository).saveAndFlush(toSave);
    }

    @Test
    void readTransactionList_returnsMappedModels() {
        var input = new ReadListInput("7", LocalDate.of(2025, 6, 1), LocalDate.of(2025, 6, 30), null, null);

        when(specFactory.ownedBy(input.userId())).thenReturn((root, q, cb) -> null);
        when(specFactory.hasTransactionDateBetween(input.startDate(), input.endDate(), TransactionEntity.class))
                .thenReturn((root, q, cb) -> null);

        var entity1 = new TransactionEntity();
        entity1.setId(1L);
        var entity2 = new TransactionEntity();
        entity2.setId(2L);
        when(transactionRepository.findAll(any(Specification.class))).thenReturn(List.of(entity1, entity2));

        var m1 = TransactionModel.builder().id(1L).amount(10).userId(7L).build();
        var m2 = TransactionModel.builder().id(2L).amount(20).userId(7L).build();
        when(transactionMapper.map(entity1)).thenReturn(m1);
        when(transactionMapper.map(entity2)).thenReturn(m2);

        var result = transactionService.readTransactionList(input);

        assertEquals(List.of(m1, m2), result);
        verify(transactionRepository).findAll(any(Specification.class));
    }
}
