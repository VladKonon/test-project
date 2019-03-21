package com.osdb.test.controller.mapper;

import com.osdb.test.controller.dto.TaskDto;
import com.osdb.test.entity.elasticsearch.ElasticSearchTask;
import com.osdb.test.entity.jpa.Task;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class TaskMapper {

    private final static Long NOT_STARTED = 1L;
    private final static Long LATE = 4L;

    public TaskDto convertToDto(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .priorityId(task.getPriorityId())
                .statusId(task.getStatusId())
                .startDate(task.getStartDate())
                .build();
    }

    public Task convertToEntity(TaskDto taskDto) {
        Task task = new Task();

        task.setId(taskDto.getId());
        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setPriorityId(taskDto.getPriorityId());
        task.setStartDate(taskDto.getStartDate());

        if (taskDto.getStartDate().isAfter(Instant.now())) {
            task.setStatusId(LATE);
        } else {
            task.setStatusId(NOT_STARTED);
        }
        return task;
    }

    public ElasticSearchTask convertToElasticSearchEntity(Task task) {
        ElasticSearchTask esTask = new ElasticSearchTask();

        esTask.setId(task.getId());
        esTask.setName(task.getName());
        esTask.setDescription(task.getDescription());
        esTask.setProjectId(task.getProjectId());

        return esTask;
    }
}
