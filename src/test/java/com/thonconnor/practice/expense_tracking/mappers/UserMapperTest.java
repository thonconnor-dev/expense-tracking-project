package com.thonconnor.practice.expense_tracking.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.thonconnor.practice.expense_tracking.entities.UserEntity;
import com.thonconnor.practice.expense_tracking.models.UserModel;

class UserMapperTest {

    private final UserMapper userMapper = new UserMapper();

    @Test
    void map_entityToModel_convertsCreatedDate() {
        var created = Timestamp.valueOf(LocalDateTime.of(2025, 6, 3, 12, 30));
        var entity = new UserEntity(5L, "bob", created);

        UserModel model = userMapper.map(entity);

        assertEquals(5L, model.getId());
        assertEquals("bob", model.getUsername());
        assertEquals(LocalDate.of(2025, 6, 3), model.getCreatedDate());
    }

    @Test
    void map_entityToModel_handlesNullCreatedDate() {
        var entity = new UserEntity(6L, "carol", null);
        UserModel model = userMapper.map(entity);
        assertEquals(6L, model.getId());
        assertEquals("carol", model.getUsername());
        assertNull(model.getCreatedDate());
    }

    @Test
    void map_entityNull_returnsNull() {
        assertNull(userMapper.map((UserEntity) null));
    }

    @Test
    void map_modelToEntity_setsUsernameAndNulls() {
        var model = UserModel.builder().username("alice").build();
        UserEntity entity = userMapper.map(model);
        assertNull(entity.getId());
        assertEquals("alice", entity.getUsername());
        assertNull(entity.getCreatedDate());
    }

    @Test
    void map_modelNull_returnsNull() {
        assertNull(userMapper.map((UserModel) null));
    }
}

