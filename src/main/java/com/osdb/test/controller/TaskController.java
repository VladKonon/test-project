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

    @GetMapping
    public ResponseEntity get(@PathVariable("projectId") Long projectId) {
        return ResponseEntity.ok(taskFacade.getAll(projectId));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity get(@PathVariable("taskId") Long taskId, @PathVariable("projectId") Long projectId) {
        return ResponseEntity.ok(taskFacade.getById(taskId, projectId));
    }

    @PostMapping
    public ResponseEntity create(@PathVariable("projectId") Long projectId,
                                 @Valid @RequestBody TaskDto taskDto) {
        return ResponseEntity.ok(taskFacade.save(projectId, taskDto));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity update(@PathVariable("taskId") Long taskId,
                                 @PathVariable("projectId") Long projectId,
                                 @Valid @RequestBody TaskDto taskDtoDetails) {
        return ResponseEntity.ok(taskFacade.update(taskId, projectId, taskDtoDetails));
    }

    @PatchMapping("/{taskId}")
    public ResponseEntity updateStatus(@PathVariable("taskId") Long taskId,
                                       @PathVariable("projectId") Long projectId) {
        return ResponseEntity.ok(taskFacade.updateStatus(taskId, projectId));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity delete(@PathVariable("taskId") Long taskId, @PathVariable("projectId") Long projectId) {
        taskFacade.delete(taskId, projectId);
        return ResponseEntity.noContent().build();
    }
}
