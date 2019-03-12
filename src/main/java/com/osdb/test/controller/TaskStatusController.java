package com.osdb.test.controller;

import com.osdb.test.service.TaskStatusService;
import io.swagger.annotations.Api;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "task-status-api", description = "REST API endpoints for Task Status")
@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/api/v1/private/task-statuses")
public class TaskStatusController {

    TaskStatusService taskStatusService;

    @GetMapping
    public ResponseEntity get() {
        return ResponseEntity.ok(taskStatusService.getAll());
    }
}
