package com.thonconnor.practice.expense_tracking.entities;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "transaction")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
    @OneToOne
    @JoinColumn(name = "category_id", nullable = false)
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
    @JoinColumn(name = "income_id", nullable = true)
    private IncomeEntity income;
    @OneToOne
    @JoinColumn(name = "expense_id", nullable = true)
    private ExpenseEntity expense;
    @Column(name = "transaction_type", nullable = false)
    private String type;

}
