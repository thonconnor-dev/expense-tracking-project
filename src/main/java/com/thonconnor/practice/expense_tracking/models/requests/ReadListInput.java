package com.thonconnor.practice.expense_tracking.models.requests;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

public record ReadListInput(@NotBlank(message = "userId cannot blank") String userId, LocalDate startDate,
        LocalDate endDate,
        PageRequest pageRequest, SortRequest sortRequest) {

}
