package com.thonconnor.practice.expense_tracking.models.requests;

import java.time.LocalDate;

public record ReadListInput(String userId, LocalDate startDate, LocalDate endDate, int pageSize) {

}
