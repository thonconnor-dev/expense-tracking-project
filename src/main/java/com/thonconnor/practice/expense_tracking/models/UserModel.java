package com.thonconnor.practice.expense_tracking.models;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    private long id;
    private String username;
    private LocalDate createdDate;
}
