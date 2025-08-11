package com.thonconnor.practice.expense_tracking.mappers;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.thonconnor.practice.expense_tracking.entities.CategoryEntity;
import com.thonconnor.practice.expense_tracking.entities.TransactionEntity;
import com.thonconnor.practice.expense_tracking.entities.UserEntity;
import com.thonconnor.practice.expense_tracking.models.TransactionModel;
import com.thonconnor.practice.expense_tracking.models.requests.TransactionRequest;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TransactionMapper {

        private final CategoryMapper categoryMapper;
        private final TransactionAssociationMapperFactory transactionAssociationMapperFactory;

        public TransactionModel map(TransactionEntity transactionEntity) {
                return Optional.ofNullable(transactionEntity).map(entity -> TransactionModel.builder()
                                .id(entity.getId())
                                .amount(entity.getAmount())
                                .description(entity.getDescription())
                                .category(categoryMapper.map(entity.getCategory()))
                                .userId(entity.getUser().getId())
                                .transactionDate(entity.getTransactionDate() == null ? null
                                                : entity.getTransactionDate().toInstant().atZone(ZoneId.systemDefault())
                                                                .toLocalDate())
                                .createdDate(entity.getCreatedDate() == null ? null
                                                : entity.getCreatedDate().toInstant().atZone(ZoneId.systemDefault())
                                                                .toLocalDate())
                                .type(transactionEntity.getType())
                                .build())
                                .orElse(null);
        }

        public TransactionEntity mapNewEntity(TransactionModel transactionModel, UserEntity userEntity,
                        CategoryEntity categoryEntity) {
                TransactionEntity transactionEntity = new TransactionEntity(null, userEntity, categoryEntity,
                                transactionModel.getAmount(), transactionModel.getDescription(), null,
                                Timestamp.valueOf(transactionModel.getTransactionDate().atStartOfDay()), null, null,
                                transactionModel.getType());

                TransactionRequest transactionRequest = new TransactionRequest(transactionEntity, userEntity,
                                categoryEntity);
                transactionAssociationMapperFactory.mapAssociation(transactionRequest);

                return transactionEntity;

        }
}
