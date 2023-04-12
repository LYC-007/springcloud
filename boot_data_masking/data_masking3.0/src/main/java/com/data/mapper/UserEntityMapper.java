package com.data.mapper;

import com.data.domain.UserEntity;
import com.data.framework.annotation.EncryptResultFieldAnnotation;
import com.data.framework.annotation.PasswordEncryptStrategy;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserEntityMapper {
    @EncryptResultFieldAnnotation(fieldKey = "password", encryptStrategy = PasswordEncryptStrategy.class)
    List<UserEntity> getAllUserList();
}
