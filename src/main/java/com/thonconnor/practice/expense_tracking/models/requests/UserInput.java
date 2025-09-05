package com.thonconnor.practice.expense_tracking.models.requests;

import jakarta.validation.constraints.NotNull;

public record UserInput(@NotNull Long userId) {

}
