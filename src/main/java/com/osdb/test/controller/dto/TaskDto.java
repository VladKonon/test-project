package com.osdb.test.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.Instant;

@Value
@Builder
public class TaskDto {

    @ApiModelProperty(hidden = true)
    Long id;

    @NotEmpty(message = "Missing required parameter: 'name'.")
    @Pattern(regexp = "^.{5,}$", message = "Name must contain more than 5 characters")
    String name;

    String description;

    Long priorityId;

    @ApiModelProperty(hidden = true)
    Long statusId;

    Instant startDate;
}
