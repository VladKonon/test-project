package com.osdb.test.controller.facade;

import com.osdb.test.controller.dto.TaskDto;
import com.osdb.test.controller.mapper.TaskMapper;
import com.osdb.test.entity.elasticsearch.ElasticSearchTask;
import com.osdb.test.entity.jpa.Task;
import com.osdb.test.service.TaskElasticSearchService;
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
    TaskElasticSearchService taskElasticSearchService;
    TaskMapper taskMapper;

    public List<ElasticSearchTask> fullTextSearch(String search) {
        return taskElasticSearchService.fullTextSearch(search);
    }

    public List<ElasticSearchTask> getByName(String name) {
        return taskElasticSearchService.findByName(name);
    }

    public List<TaskDto> getAll(Long projectId) {
        List<Task> taskList = taskService.getAll(projectId);
        return taskList.stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }

    public TaskDto getById(Long projectId, Long taskId) {
        return taskMapper.convertToDto(taskService.get(taskId, projectId));
    }

    public TaskDto save(Long projectId, TaskDto taskDto) {
        Task task = taskMapper.convertToEntity(taskDto);
        task.setProjectId(projectId);
        Task updatedTask = taskService.save(task);
        taskElasticSearchService.save(taskMapper.convertToElasticSearchEntity(updatedTask));

        return taskMapper.convertToDto(updatedTask);
    }

    public TaskDto update(Long projectId, Long taskId, TaskDto taskDtoDetails) {
        Task task = taskService.update(taskId, projectId, taskMapper.convertToEntity(taskDtoDetails));
        taskElasticSearchService.save(taskMapper.convertToElasticSearchEntity(task));
        return taskMapper.convertToDto(task);
    }

    public TaskDto updateStatus(Long projectId, Long taskId) {
        Task task = taskService.updateStatus(taskId, projectId);

        return taskMapper.convertToDto(task);
    }

    public void delete(Long projectId, Long taskId) {
        taskService.delete(taskId, projectId);
        taskElasticSearchService.delete(taskId);
    }
}
