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
@Table(name = "income")
public class IncomeEntity {
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
    @Column(name = "income_date", nullable = false)
    private Timestamp incomeDate;

    public IncomeEntity(Long id, UserEntity user, CategoryEntity category, Double amount, String description,
            Timestamp createdDate, Timestamp incomeDate) {
        this.id = id;
        this.user = user;
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.createdDate = createdDate;
        this.incomeDate = incomeDate;
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

    public Timestamp getIncomeDate() {
        return incomeDate;
    }

    public void setIncomeDate(Timestamp incomeDate) {
        this.incomeDate = incomeDate;
    }

}
