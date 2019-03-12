package com.osdb.test.controller.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AuthDto {

    UserDto user;

    String accessToken;

    String refreshToken;
}
