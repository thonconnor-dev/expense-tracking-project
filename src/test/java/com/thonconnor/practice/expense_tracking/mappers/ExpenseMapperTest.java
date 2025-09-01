package com.thonconnor.practice.expense_tracking.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.thonconnor.practice.expense_tracking.entities.CategoryEntity;
import com.thonconnor.practice.expense_tracking.entities.ExpenseEntity;
import com.thonconnor.practice.expense_tracking.entities.TransactionEntity;
import com.thonconnor.practice.expense_tracking.entities.UserEntity;
import com.thonconnor.practice.expense_tracking.models.CategoryModel;
import com.thonconnor.practice.expense_tracking.models.ExpenseModel;
import com.thonconnor.practice.expense_tracking.models.requests.TransactionRequest;

@ExtendWith(MockitoExtension.class)
class ExpenseMapperTest {

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private ExpenseMapper expenseMapper;

    @Test
    void map_convertsTimestampsAndCategory() {
        var user = new UserEntity(101L, "alice", null);
        var cat = new CategoryEntity(7L, "Groceries", "Expense");
        var created = Timestamp.valueOf(LocalDateTime.of(2025, 3, 2, 10, 0));
        var expenseDate = Timestamp.valueOf(LocalDateTime.of(2025, 3, 1, 0, 0));
        var entity = new ExpenseEntity(21L, user, cat, 42.50, "Food", created, expenseDate);

        var catModel = CategoryModel.builder().id(7L).name("Groceries").type("Expense").build();
        when(categoryMapper.map(cat)).thenReturn(catModel);

        ExpenseModel model = expenseMapper.map(entity);

        assertEquals(21L, model.getId());
        assertEquals(42.50, model.getAmount());
        assertEquals(101L, model.getUserId());
        assertEquals(LocalDate.of(2025, 3, 1), model.getExpenseDate());
        assertEquals(LocalDate.of(2025, 3, 2), model.getCreatedDate());
        assertEquals(catModel, model.getCategory());
    }

    @Test
    void mapNewExpense_buildsEntityFromTransactionRequest() {
        var user = new UserEntity(101L, "alice", null);
        var cat = new CategoryEntity(7L, "Groceries", "Expense");
        var tx = new TransactionEntity();
        tx.setAmount(15.0);
        tx.setDescription("Snacks");
        tx.setTransactionDate(Timestamp.valueOf(LocalDateTime.of(2025, 4, 1, 0, 0)));

        var request = new TransactionRequest(tx, user, cat);

        ExpenseEntity entity = expenseMapper.mapNewExpense(request);

        assertEquals(user, entity.getUser());
        assertEquals(cat, entity.getCategory());
        assertEquals(15.0, entity.getAmount());
        assertEquals("Snacks", entity.getDescription());
        assertEquals(tx.getTransactionDate(), entity.getExpenseDate());
    }
}
