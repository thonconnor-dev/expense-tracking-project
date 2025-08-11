package com.thonconnor.practice.expense_tracking.mappers;

import java.time.ZoneId;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.thonconnor.practice.expense_tracking.entities.IncomeEntity;
import com.thonconnor.practice.expense_tracking.models.IncomeModel;
import com.thonconnor.practice.expense_tracking.models.requests.TransactionRequest;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class IncomeMapper {
        private final CategoryMapper categoryMapper;

        public IncomeModel map(IncomeEntity incomeEntity) {
                return Optional.ofNullable(incomeEntity)
                                .map(entity -> IncomeModel.builder()
                                                .id(incomeEntity.getId())
                                                .amount(incomeEntity.getAmount())
                                                .incomeDate(incomeEntity.getIncomeDate() == null ? null
                                                                : incomeEntity.getIncomeDate().toInstant()
                                                                                .atZone(ZoneId.systemDefault())
                                                                                .toLocalDate())
                                                .createdDate(incomeEntity.getCreatedDate() == null ? null
                                                                : incomeEntity.getCreatedDate().toInstant()
                                                                                .atZone(ZoneId.systemDefault())
                                                                                .toLocalDate())
                                                .category(categoryMapper.map(incomeEntity.getCategory()))
                                                .userId(incomeEntity.getUser().getId())
                                                .description(incomeEntity.getDescription())
                                                .build())
                                .orElse(null);
        }

        public IncomeEntity mapNewIncome(TransactionRequest transactionRequest) {
                return new IncomeEntity(null, transactionRequest.userEntity(), transactionRequest.categoryEntity(),
                                transactionRequest.transactionEntity().getAmount(),
                                transactionRequest.transactionEntity().getDescription(), null,
                                transactionRequest.transactionEntity().getTransactionDate());
        }

}
