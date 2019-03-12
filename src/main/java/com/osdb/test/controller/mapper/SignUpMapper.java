package com.osdb.test.controller.mapper;

import com.osdb.test.controller.dto.SignUpDto;
import com.osdb.test.entity.User;
import org.springframework.stereotype.Component;

@Component
public class SignUpMapper {

    public SignUpDto convertToDto(User user) {
        return SignUpDto.builder()
                .email(user.getUsername())
                .password(user.getPassword())
                .build();
    }

    public User convertToEntity(SignUpDto signUpDto) {
        User user = new User();

        user.setUsername(signUpDto.getEmail());
        user.setPassword(signUpDto.getPassword());

        return user;
    }
}
