package com.osdb.test.service;

import com.osdb.test.entity.jpa.TaskStatus;

import java.util.List;

public interface TaskStatusService {

    List<TaskStatus> getAll();
}
