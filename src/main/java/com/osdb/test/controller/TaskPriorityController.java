package com.osdb.test.controller;

import com.osdb.test.service.TaskPriorityService;
import io.swagger.annotations.Api;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "task-priority-api", description = "REST API endpoints for Task Priority")
@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/api/v1/private/task-priorities")
public class TaskPriorityController {

    TaskPriorityService taskPriorityService;

    @GetMapping
    public ResponseEntity get() {
        return ResponseEntity.ok(taskPriorityService.getAll());
    }
}
