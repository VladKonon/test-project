package com.osdb.test.controller.mapper;

import com.osdb.test.controller.dto.ProjectDto;
import com.osdb.test.entity.Project;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ProjectMapper {

    public ProjectDto convertToDto(Project project) {

        return ProjectDto.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .build();
    }

    public Project convertToEntity(ProjectDto projectDto) {
        Project project = new Project();

        project.setId(projectDto.getId());
        project.setName(projectDto.getName());
        project.setDescription(projectDto.getDescription());

        return project;
    }
}
