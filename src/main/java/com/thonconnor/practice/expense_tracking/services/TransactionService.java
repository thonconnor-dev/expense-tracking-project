package com.thonconnor.practice.expense_tracking.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.thonconnor.practice.expense_tracking.entities.CategoryEntity;
import com.thonconnor.practice.expense_tracking.entities.TransactionEntity;
import com.thonconnor.practice.expense_tracking.entities.UserEntity;
import com.thonconnor.practice.expense_tracking.mappers.TransactionMapper;
import com.thonconnor.practice.expense_tracking.models.TransactionModel;
import com.thonconnor.practice.expense_tracking.repositories.CategoryRepository;
import com.thonconnor.practice.expense_tracking.repositories.TransactionRepository;
import com.thonconnor.practice.expense_tracking.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class TransactionService {
    private final TransactionMapper transactionMapper;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    public TransactionModel createTransaction(TransactionModel transactionModel) {
        log.info("create transaction input={}", transactionModel);
        Optional<CategoryEntity> categoryEntityOpt = categoryRepository
                .findById(transactionModel.getCategory().getId());
        Optional<UserEntity> userEntityOpt = userRepository.findById(transactionModel.getUserId());

        TransactionEntity transactionEntity = transactionMapper.mapNewEntity(transactionModel, userEntityOpt.get(),
                categoryEntityOpt.get());
        transactionEntity = transactionRepository.saveAndFlush(transactionEntity);
        transactionModel.setId(transactionEntity.getId());
        return transactionModel;
    }

}
