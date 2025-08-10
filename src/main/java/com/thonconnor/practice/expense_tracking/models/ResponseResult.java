package com.thonconnor.practice.expense_tracking.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseResult<T extends BaseModel> {
    private T data;
    private List<Error> errors;
}
