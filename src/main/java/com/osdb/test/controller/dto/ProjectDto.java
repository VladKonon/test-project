package com.osdb.test.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Value
@Builder
public class ProjectDto {

    @ApiModelProperty(hidden = true)
    Long id;

    @NotNull(message = "Missing required parameter: 'name'.")
    @Pattern(regexp = "^.{5,}$", message = "Name must contain more than 5 characters")
    String name;

    String description;

}
