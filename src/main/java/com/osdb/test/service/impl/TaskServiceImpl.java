package com.osdb.test.service.impl;

import com.osdb.test.entity.Task;
import com.osdb.test.exception.NotFoundException;
import com.osdb.test.repository.TaskRepository;
import com.osdb.test.service.TaskService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TaskServiceImpl implements TaskService {

    private final static String PROJECT_NOT_FOUND = "Task not found.";
    private final static String ILLEGAL_TASK_STATE = "Illegal task state";
    private final static long NOT_STARTED = 1L;
    private final static long IN_PROGRESS = 2L;
    private final static long COMPLETED = 3L;
    private final static long LATE = 4L;

    TaskRepository taskRepository;

    @Override
    public List<Task> getAll(Long projectId) {
        return taskRepository.findAllByProjectId(projectId);
    }

    @Override
    public Task get(Long taskId, Long projectId) {
        return taskRepository
                .findByIdAndProjectId(taskId, projectId)
                .orElseThrow(() -> new NotFoundException(PROJECT_NOT_FOUND));
    }

    @Override
    public Task save(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task update(Long taskId, Long projectId, Task taskDetails) {
        Task task = get(taskId, projectId);

        task.setName(taskDetails.getName());
        task.setDescription(taskDetails.getDescription());

        return save(task);
    }

    @Override
    public Task updateStatus(Long projectId, Long taskId) {
        Task task = get(taskId, projectId);

        if ( task.getStatusId() == NOT_STARTED || task.getStatusId() == LATE) {
            task.setStatusId(IN_PROGRESS);
        } else if (task.getStatusId() == IN_PROGRESS) {
            task.setStatusId(COMPLETED);
        } else {
            throw new IllegalStateException(ILLEGAL_TASK_STATE);
        }

        return save(task);
    }

    @Override
    public void delete(Long taskId, Long projectId) {
        Task task = get(taskId, projectId);
        taskRepository.delete(task);
    }
}
