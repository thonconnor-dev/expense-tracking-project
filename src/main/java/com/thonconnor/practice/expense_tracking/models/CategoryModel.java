package com.thonconnor.practice.expense_tracking.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class CategoryModel extends BaseModel {
    private long id;
    private String name;
    private String type;

}
