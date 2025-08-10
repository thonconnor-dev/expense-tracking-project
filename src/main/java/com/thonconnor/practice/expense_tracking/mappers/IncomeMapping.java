package com.thonconnor.practice.expense_tracking.mappers;

import java.time.ZoneId;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.thonconnor.practice.expense_tracking.entities.IncomeEntity;
import com.thonconnor.practice.expense_tracking.models.IncomeModel;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class IncomeMapping {
    private final CategoryMapper categoryMapper;

    public IncomeModel map(IncomeEntity incomeEntity) {
        return Optional.ofNullable(incomeEntity)
                .map(entity -> IncomeModel.builder()
                        .id(incomeEntity.getId())
                        .amount(incomeEntity.getAmount())
                        .incomeDate(incomeEntity.getIncomeDate() == null ? null
                                : incomeEntity.getIncomeDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                        .createdDate(incomeEntity.getCreatedDate() == null ? null
                                : incomeEntity.getCreatedDate().toInstant().atZone(ZoneId.systemDefault())
                                        .toLocalDate())
                        .category(categoryMapper.map(incomeEntity.getCategory()))
                        .userId(incomeEntity.getUser().getId())
                        .description(incomeEntity.getDescription())
                        .build())
                .orElse(null);
    }

}
