package com.thonconnor.practice.expense_tracking.entities;

import java.sql.Timestamp;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @Generated(value = "AUTO")
    private Long id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "created_date", nullable = false)
    private Timestamp createdDate;

    public UserEntity(Long id, String username, Timestamp createdDate) {
        this.id = id;
        this.username = username;
        this.createdDate = createdDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }
}
