package com.osdb.test.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Value
@Builder
public class UserDto {

    @ApiModelProperty(hidden = true)
    Long id;

    @NotNull
    @Pattern(regexp = "[\\w]+@([\\w]+\\.)+[\\w]+", message = "Incorrect email")
    String email;

    @NotNull
    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}",
            message = "Password must have minimum eight characters, at least one uppercase letter," +
                    " one lowercase letter, one number and one special character")
    @JsonIgnore
    String password;
}
