package com.thonconnor.practice.expense_tracking.mappers;

import java.time.ZoneId;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.thonconnor.practice.expense_tracking.entities.UserEntity;
import com.thonconnor.practice.expense_tracking.models.UserModel;

@Component
public class UserMapper {

        public UserModel map(UserEntity userEntity) {
                return Optional.ofNullable(userEntity)
                                .map(entity -> UserModel.builder()
                                                .id(entity.getId())
                                                .username(entity.getUsername())
                                                .createdDate(null != entity.getCreatedDate()
                                                                ? entity.getCreatedDate().toInstant()
                                                                                .atZone(ZoneId.systemDefault())
                                                                                .toLocalDate()
                                                                : null)
                                                .build())
                                .orElse(null);
        }

        public UserEntity map(UserModel userModel) {
                return Optional.ofNullable(userModel)
                                .map(model -> new UserEntity(null, model.getUsername(), null))
                                .orElse(null);
        }

}
