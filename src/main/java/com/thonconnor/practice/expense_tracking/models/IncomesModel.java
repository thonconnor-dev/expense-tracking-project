package com.thonconnor.practice.expense_tracking.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class IncomesModel extends BaseModel {

    private List<IncomeModel> incomes;

}
