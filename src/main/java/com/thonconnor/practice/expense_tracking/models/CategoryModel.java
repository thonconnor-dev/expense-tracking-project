package com.thonconnor.practice.expense_tracking.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CategoryModel {
    private long id;
    private String name;
    private String type;

}
