package com.osdb.test.controller.response;

import com.osdb.test.controller.dto.ProjectDto;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class PageResponse {

    Long total;
    List<ProjectDto> projectDtoList;
}
