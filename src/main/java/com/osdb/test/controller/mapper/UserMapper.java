package com.osdb.test.controller.mapper;

import com.osdb.test.controller.dto.UserDto;
import com.osdb.test.entity.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserMapper {

    PasswordEncoder passwordEncoder;

    public UserDto convertToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getUsername())
                .password(user.getPassword())
                .build();
    }

    public User convertToEntity(UserDto userDto) {
        User user = new User();

        user.setId(userDto.getId());
        user.setUsername(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        return user;
    }
}
