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
import com.thonconnor.practice.expense_tracking.entities.IncomeEntity;
import com.thonconnor.practice.expense_tracking.entities.TransactionEntity;
import com.thonconnor.practice.expense_tracking.entities.UserEntity;
import com.thonconnor.practice.expense_tracking.models.CategoryModel;
import com.thonconnor.practice.expense_tracking.models.IncomeModel;
import com.thonconnor.practice.expense_tracking.models.requests.TransactionRequest;

@ExtendWith(MockitoExtension.class)
class IncomeMapperTest {

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private IncomeMapper incomeMapper;

    @Test
    void map_convertsTimestampsAndCategory() {
        var user = new UserEntity(101L, "alice", null);
        var cat = new CategoryEntity(5L, "Salary", "Income");
        var created = Timestamp.valueOf(LocalDateTime.of(2025, 1, 2, 10, 0));
        var incomeDate = Timestamp.valueOf(LocalDateTime.of(2025, 1, 1, 0, 0));
        var entity = new IncomeEntity(11L, user, cat, 1234.56, "Jan salary", created, incomeDate);

        var catModel = CategoryModel.builder().id(5L).name("Salary").type("Income").build();
        when(categoryMapper.map(cat)).thenReturn(catModel);

        IncomeModel model = incomeMapper.map(entity);

        assertEquals(11L, model.getId());
        assertEquals(1234.56, model.getAmount());
        assertEquals(101L, model.getUserId());
        assertEquals(LocalDate.of(2025, 1, 1), model.getIncomeDate());
        assertEquals(LocalDate.of(2025, 1, 2), model.getCreatedDate());
        assertEquals(catModel, model.getCategory());
    }

    @Test
    void mapNewIncome_buildsEntityFromTransactionRequest() {
        var user = new UserEntity(101L, "alice", null);
        var cat = new CategoryEntity(5L, "Salary", "Income");
        var tx = new TransactionEntity();
        tx.setAmount(88.0);
        tx.setDescription("Bonus");
        tx.setTransactionDate(Timestamp.valueOf(LocalDateTime.of(2025, 2, 1, 0, 0)));

        var request = new TransactionRequest(tx, user, cat);

        IncomeEntity entity = incomeMapper.mapNewIncome(request);

        assertEquals(user, entity.getUser());
        assertEquals(cat, entity.getCategory());
        assertEquals(88.0, entity.getAmount());
        assertEquals("Bonus", entity.getDescription());
        assertEquals(tx.getTransactionDate(), entity.getIncomeDate());
    }
}
