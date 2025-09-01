package com.thonconnor.practice.expense_tracking.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.thonconnor.practice.expense_tracking.entities.ExpenseEntity;
import com.thonconnor.practice.expense_tracking.entities.IncomeEntity;
import com.thonconnor.practice.expense_tracking.entities.TransactionEntity;
import com.thonconnor.practice.expense_tracking.entities.UserEntity;
import com.thonconnor.practice.expense_tracking.entities.CategoryEntity;
import com.thonconnor.practice.expense_tracking.models.requests.TransactionRequest;

@ExtendWith(MockitoExtension.class)
class TransactionAssociationMapperFactoryTest {

    @Mock private IncomeMapper incomeMapper;
    @Mock private ExpenseMapper expenseMapper;

    @InjectMocks private TransactionAssociationMapperFactory factory;

    @Test
    void mapAssociation_income_setsIncomeEntity() {
        var user = new UserEntity(1L, "u", null);
        var cat = new CategoryEntity(2L, "c", "Income");
        var tx = new TransactionEntity();
        tx.setType("INCOME");
        var req = new TransactionRequest(tx, user, cat);

        var newIncome = new IncomeEntity();
        when(incomeMapper.mapNewIncome(req)).thenReturn(newIncome);

        factory.mapAssociation(req);

        verify(incomeMapper).mapNewIncome(req);
        assertEquals(newIncome, tx.getIncome());
    }

    @Test
    void mapAssociation_expense_setsExpenseEntity() {
        var user = new UserEntity(1L, "u", null);
        var cat = new CategoryEntity(2L, "c", "Expense");
        var tx = new TransactionEntity();
        tx.setType("EXPENSE");
        var req = new TransactionRequest(tx, user, cat);

        var newExpense = new ExpenseEntity();
        when(expenseMapper.mapNewExpense(req)).thenReturn(newExpense);

        factory.mapAssociation(req);

        verify(expenseMapper).mapNewExpense(req);
        assertEquals(newExpense, tx.getExpense());
    }
}

