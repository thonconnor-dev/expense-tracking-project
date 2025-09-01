package com.thonconnor.practice.expense_tracking.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.thonconnor.practice.expense_tracking.entities.IncomeEntity;

@Repository
public interface IncomeRepository extends CrudRepository<IncomeEntity, Long>, JpaSpecificationExecutor<IncomeEntity> {
}
