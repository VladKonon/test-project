package com.osdb.test.controller.facade;

import com.osdb.test.controller.dto.TaskDto;
import com.osdb.test.controller.mapper.TaskMapper;
import com.osdb.test.entity.Task;
import com.osdb.test.service.TaskService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TaskFacade {

    TaskService taskService;
    TaskMapper taskMapper;

    public List<TaskDto> getAll(Long projectId) {
        List<Task> taskList = taskService.getAll(projectId);
        return taskList.stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }

    public TaskDto getById(Long taskId, Long projectId) {
        return taskMapper.convertToDto(taskService.get(taskId, projectId));
    }

    public TaskDto save(Long projectId, TaskDto taskDto) {
        Task task = taskMapper.convertToEntity(taskDto);
        task.setProjectId(projectId);

        return taskMapper.convertToDto(taskService.save(task));
    }

    public TaskDto update(Long taskId, Long projectId, TaskDto taskDtoDetails) {
        Task task = taskService.update(taskId, projectId, taskMapper.convertToEntity(taskDtoDetails));
        return taskMapper.convertToDto(task);
    }

    public TaskDto updateStatus(Long taskId, Long projectId) {
        Task task = taskService.updateStatus(taskId, projectId);

        return taskMapper.convertToDto(task);
    }

    public void delete(Long taskId, Long projectId) {
        taskService.delete(taskId, projectId);
    }
}
