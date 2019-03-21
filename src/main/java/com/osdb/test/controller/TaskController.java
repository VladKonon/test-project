package com.osdb.test.controller;

import com.osdb.test.controller.dto.TaskDto;
import com.osdb.test.controller.facade.TaskFacade;
import io.swagger.annotations.Api;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "task-api", description = "REST API endpoints for Task")
@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/api/v1/private/projects/{projectId}/tasks")
public class TaskController {

    TaskFacade taskFacade;

    @GetMapping("/multi_match_search")
    public ResponseEntity fulltextSearch(String search) {
        return ResponseEntity.ok(taskFacade.fullTextSearch(search));
    }

    @GetMapping("/search")
    public ResponseEntity searchByName(String name) {
        return ResponseEntity.ok(taskFacade.getByName(name));
    }

    @GetMapping
    public ResponseEntity get(@PathVariable("projectId") Long projectId) {
        return ResponseEntity.ok(taskFacade.getAll(projectId));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity get(@PathVariable("projectId") Long projectId, @PathVariable("taskId") Long taskId) {
        return ResponseEntity.ok(taskFacade.getById(taskId, projectId));
    }

    @PostMapping
    public ResponseEntity create(@PathVariable("projectId") Long projectId,
                                 @Valid @RequestBody TaskDto taskDto) {
        return ResponseEntity.ok(taskFacade.save(projectId, taskDto));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity update(@PathVariable("projectId") Long projectId,
                                 @PathVariable("taskId") Long taskId,
                                 @Valid @RequestBody TaskDto taskDtoDetails) {
        return ResponseEntity.ok(taskFacade.update(taskId, projectId, taskDtoDetails));
    }

    @PatchMapping("/{taskId}")
    public ResponseEntity updateStatus(@PathVariable("projectId") Long projectId,
                                       @PathVariable("taskId") Long taskId) {
        return ResponseEntity.ok(taskFacade.updateStatus(taskId, projectId));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity delete(@PathVariable("projectId") Long projectId, @PathVariable("taskId") Long taskId) {
        taskFacade.delete(taskId, projectId);
        return ResponseEntity.noContent().build();
    }
}
