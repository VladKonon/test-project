package com.osdb.test.service;

import com.osdb.test.entity.jpa.TaskPriority;

import java.util.List;

public interface TaskPriorityService {

    List<TaskPriority> getAll();
}
