package com.thonconnor.practice.expense_tracking.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.thonconnor.practice.expense_tracking.entities.ExpenseEntity;

@Repository
public interface ExpenseRepository
        extends CrudRepository<ExpenseEntity, Long>, JpaSpecificationExecutor<ExpenseEntity> {
}
