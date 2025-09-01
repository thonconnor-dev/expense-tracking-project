package com.thonconnor.practice.expense_tracking.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.thonconnor.practice.expense_tracking.entities.CategoryEntity;
import com.thonconnor.practice.expense_tracking.entities.TransactionEntity;
import com.thonconnor.practice.expense_tracking.entities.UserEntity;
import com.thonconnor.practice.expense_tracking.models.CategoryModel;
import com.thonconnor.practice.expense_tracking.models.TransactionModel;
import com.thonconnor.practice.expense_tracking.models.requests.TransactionInput;
import com.thonconnor.practice.expense_tracking.models.requests.TransactionRequest;

@ExtendWith(MockitoExtension.class)
class TransactionMapperTest {

    @Mock
    private CategoryMapper categoryMapper;
    @Mock
    private TransactionAssociationMapperFactory associationFactory;

    @InjectMocks
    private TransactionMapper mapper;

    @Test
    void map_entityToModel_convertsDatesAndCategory() {
        var user = new UserEntity(7L, "bob", null);
        var category = new CategoryEntity(3L, "Food", "Expense");
        var created = Timestamp.valueOf(LocalDateTime.of(2025, 5, 2, 12, 0));
        var txDate = Timestamp.valueOf(LocalDateTime.of(2025, 5, 1, 0, 0));
        var entity = new TransactionEntity(99L, user, category, 12.34, "Coffee", created, txDate, null, null,
                "EXPENSE");

        var categoryModel = CategoryModel.builder().id(3L).name("Food").type("Expense").build();
        when(categoryMapper.map(category)).thenReturn(categoryModel);

        var model = mapper.map(entity);

        assertEquals(99L, model.getId());
        assertEquals(12.34, model.getAmount());
        assertEquals("Coffee", model.getDescription());
        assertEquals(7L, model.getUserId());
        assertEquals(LocalDate.of(2025, 5, 1), model.getTransactionDate());
        assertEquals(LocalDate.of(2025, 5, 2), model.getCreatedDate());
        assertEquals("EXPENSE", model.getType());
        assertEquals(categoryModel, model.getCategory());
    }

    @Test
    void map_entityNull_returnsNull() {
        assertNull(mapper.map((TransactionEntity) null));
    }

    @Test
    void map_inputToModel_setsFields() {
        var input = new TransactionInput("Coffee", 4.5, "EXPENSE", 3L,
                new com.thonconnor.practice.expense_tracking.models.requests.UserInput(101L),
                LocalDate.of(2025, 6, 15));

        var model = mapper.map(input);

        assertEquals(4.5, model.getAmount());
        assertEquals("Coffee", model.getDescription());
        assertEquals(101L, model.getUserId());
        assertEquals("EXPENSE", model.getType());
        assertEquals(LocalDate.of(2025, 6, 15), model.getTransactionDate());
        assertEquals(3L, model.getCategory().getId());
    }

    @Test
    void mapNewEntity_buildsEntity_andInvokesAssociationFactory() {
        var model = TransactionModel.builder()
                .amount(10.0)
                .description("snack")
                .type("EXPENSE")
                .transactionDate(LocalDate.of(2025, 7, 1))
                .build();
        var user = new UserEntity(7L, "bob", null);
        var category = new CategoryEntity(3L, "Food", "Expense");

        ArgumentCaptor<TransactionRequest> captor = ArgumentCaptor.forClass(TransactionRequest.class);

        var entity = mapper.mapNewEntity(model, user, category);

        // verify association factory invocation
        verify(associationFactory).mapAssociation(captor.capture());
        var req = captor.getValue();
        // request contains the same refs
        assertEquals(user, req.userEntity());
        assertEquals(category, req.categoryEntity());

        // basic field assertions
        assertEquals(user, entity.getUser());
        assertEquals(category, entity.getCategory());
        assertEquals(10.0, entity.getAmount());
        assertEquals("snack", entity.getDescription());
        assertEquals("EXPENSE", entity.getType());
        assertEquals(Timestamp.valueOf(LocalDate.of(2025, 7, 1).atStartOfDay()), entity.getTransactionDate());
        // createdDate is not set by mapper
        assertNull(entity.getCreatedDate());
    }
}
