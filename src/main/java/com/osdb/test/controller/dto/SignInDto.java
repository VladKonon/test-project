package com.osdb.test.controller.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SignInDto {

    String email;

    String password;
}
