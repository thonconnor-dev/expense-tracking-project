package com.thonconnor.practice.expense_tracking.filters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.thonconnor.practice.expense_tracking.entities.ExpenseEntity;
import com.thonconnor.practice.expense_tracking.entities.IncomeEntity;
import com.thonconnor.practice.expense_tracking.entities.TransactionEntity;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@ExtendWith(MockitoExtension.class)
class TransactionFilterSpecificationTest {

    // Use raw type to avoid generics mismatch for classType argument
    private final TransactionFilterSpecification specification = new TransactionFilterSpecification();

    @Mock
    private Root<Object> root;

    @Mock
    private CriteriaQuery<?> query;

    @Mock
    private CriteriaBuilder cb;

    @Mock
    private Predicate predicate;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    void ownedBy_withUserId_buildsEqualityOnUserId() {
        Path userPath = (Path) org.mockito.Mockito.mock(Path.class);
        Path idPath = (Path) org.mockito.Mockito.mock(Path.class);
        when(root.get("user")).thenReturn(userPath);
        when(userPath.get("id")).thenReturn(idPath);
        when(cb.equal(idPath, "42")).thenReturn(predicate);

        var spec = specification.ownedBy("42");
        var result = spec.toPredicate(root, query, cb);

        assertEquals(predicate, result);
        verify(root).get("user");
        verify(userPath).get("id");
        verify(cb).equal(idPath, "42");
    }

    @Test
    void ownedBy_withNullUserId_returnsNullPredicate() {
        var spec = specification.ownedBy(null);
        var result = spec.toPredicate(root, query, cb);
        assertNull(result);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    void hasTransactionDateBetween_forIncomeEntity_usesIncomeDate() {
        LocalDate start = LocalDate.of(2024, 1, 1);
        LocalDate end = LocalDate.of(2024, 1, 31);

        Path datePath = (Path) org.mockito.Mockito.mock(Path.class);
        when(root.get("incomeDate")).thenReturn(datePath);

        when(cb.between(
                org.mockito.Mockito.eq(datePath),
                org.mockito.Mockito.any(Timestamp.class),
                org.mockito.Mockito.any(Timestamp.class))).thenReturn(predicate);

        var spec = specification.hasTransactionDateBetween(start, end, IncomeEntity.class);
        var result = spec.toPredicate(root, query, cb);

        assertEquals(predicate, result);

        ArgumentCaptor<Timestamp> startCaptor = ArgumentCaptor.forClass(Timestamp.class);
        ArgumentCaptor<Timestamp> endCaptor = ArgumentCaptor.forClass(Timestamp.class);
        verify(cb).between(org.mockito.Mockito.eq(datePath), startCaptor.capture(), endCaptor.capture());

        assertEquals(Timestamp.valueOf(start.atStartOfDay()), startCaptor.getValue());
        assertEquals(Timestamp.valueOf(end.atTime(LocalTime.MAX)), endCaptor.getValue());
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    void hasTransactionDateBetween_forTransactionEntity_usesTransactionDate() {
        LocalDate start = LocalDate.of(2025, 6, 1);
        LocalDate end = LocalDate.of(2025, 6, 30);

        Path datePath = (Path) org.mockito.Mockito.mock(Path.class);
        when(root.get("transactionDate")).thenReturn(datePath);

        when(cb.between(
                org.mockito.Mockito.eq(datePath),
                org.mockito.Mockito.any(Timestamp.class),
                org.mockito.Mockito.any(Timestamp.class))).thenReturn(predicate);

        var spec = specification.hasTransactionDateBetween(start, end, TransactionEntity.class);
        var result = spec.toPredicate(root, query, cb);

        assertEquals(predicate, result);
        verify(root).get("transactionDate");
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    void hasTransactionDateBetween_forExpenseEntity_usesExpenseDate() {
        LocalDate start = LocalDate.of(2023, 3, 1);
        LocalDate end = LocalDate.of(2023, 3, 15);

        Path datePath = (Path) org.mockito.Mockito.mock(Path.class);
        when(root.get("expenseDate")).thenReturn(datePath);

        when(cb.between(
                org.mockito.Mockito.eq(datePath),
                org.mockito.Mockito.any(Timestamp.class),
                org.mockito.Mockito.any(Timestamp.class))).thenReturn(predicate);

        var spec = specification.hasTransactionDateBetween(start, end, ExpenseEntity.class);
        var result = spec.toPredicate(root, query, cb);

        assertEquals(predicate, result);
        verify(root).get("expenseDate");
    }

    @Test
    void hasTransactionDateBetween_withNullDates_returnsNullPredicate() {
        var spec1 = specification.hasTransactionDateBetween(null, LocalDate.now(), TransactionEntity.class);
        var spec2 = specification.hasTransactionDateBetween(LocalDate.now(), null, TransactionEntity.class);

        assertNull(spec1.toPredicate(root, query, cb));
        assertNull(spec2.toPredicate(root, query, cb));
    }
}
