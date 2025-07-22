package com.thonconnor.practice.expense_tracking.entities;

import java.sql.Timestamp;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "transaction")
public class TransactionEntity {
    @Id
    @Generated(value = "AUTO")
    private Long id;
    @ManyToOne
    private UserEntity user;
    @OneToOne
    private CategoryEntity category;
    @Column(name = "amount", nullable = false)
    private Double amount;
    @Column(name = "description")
    private String description;
    @Column(name = "created_date", nullable = false)
    private Timestamp createdDate;
    @Column(name = "transaction_date", nullable = false)
    private Timestamp transactionDate;
    @OneToOne
    private IncomeEntity income;
    @OneToOne
    private ExpenseEntity expense;

    public TransactionEntity(Long id, UserEntity user, CategoryEntity category, Double amount, String description,
            Timestamp createdDate, Timestamp transactionDate, IncomeEntity income, ExpenseEntity expense) {
        this.id = id;
        this.user = user;
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.createdDate = createdDate;
        this.transactionDate = transactionDate;
        this.income = income;
        this.expense = expense;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Timestamp transactionDate) {
        this.transactionDate = transactionDate;
    }

    public IncomeEntity getIncome() {
        return income;
    }

    public void setIncome(IncomeEntity income) {
        this.income = income;
    }

    public ExpenseEntity getExpense() {
        return expense;
    }

    public void setExpense(ExpenseEntity expense) {
        this.expense = expense;
    }

}
