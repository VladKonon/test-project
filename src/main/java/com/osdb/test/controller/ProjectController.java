package com.osdb.test.controller;

import com.osdb.test.controller.dto.ProjectDto;
import com.osdb.test.controller.facade.ProjectFacade;
import com.osdb.test.controller.response.PageResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@Api(tags = "project-api", description = "REST API endpoints for Project")
@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/api/v1/private/projects")
public class ProjectController {

    ProjectFacade projectFacade;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query")
    })
    @GetMapping
    @ResponseBody
    public PageResponse get(@ApiIgnore Pageable pageable, String search) {
        Page<ProjectDto> pageResult = projectFacade.getAll(pageable, search);

            return PageResponse.builder()
                    .total(pageResult.getTotalElements())
                    .projectDtoList(pageResult.getContent())
                    .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(projectFacade.getById(id));
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody ProjectDto projectDto) {
        return ResponseEntity.ok(projectFacade.save(projectDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@Valid @PathVariable(value = "id") Long id,
                                 @RequestBody ProjectDto projectDtoDetails) {
         return ResponseEntity.ok(projectFacade.update(id, projectDtoDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable( value = "id") Long id) {
        projectFacade.delete(id);
        return ResponseEntity.noContent().build();
    }
}
