package com.osdb.test.service;

import com.osdb.test.entity.jpa.Task;

import java.util.List;

public interface TaskService {

    List<Task> getAll(Long projectId);

    Task get(Long taskId, Long projectId);

    Task save(Task task);

    Task update(Long taskId, Long projectId, Task taskDetails);

    Task updateStatus(Long projectId, Long taskId);

    void delete(Long taskId, Long projectId);
}
