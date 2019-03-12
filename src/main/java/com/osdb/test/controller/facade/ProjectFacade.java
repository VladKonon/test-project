package com.osdb.test.controller.facade;

import com.osdb.test.controller.mapper.ProjectMapper;
import com.osdb.test.controller.dto.ProjectDto;
import com.osdb.test.entity.Project;
import com.osdb.test.exception.NotFoundException;
import com.osdb.test.service.ProjectService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProjectFacade {

    ProjectService projectService;
    ProjectMapper projectMapper;

    public Page<ProjectDto> getAll(Pageable pageable, String search) {
        Page<Project> page = projectService.getAll(pageable, search);
        return page.map(projectMapper::convertToDto);

    }

    public ProjectDto getById(Long id) throws NotFoundException {
        return projectMapper.convertToDto(projectService.get(id));
    }

    public ProjectDto save(ProjectDto projectDto) {
        Project project = projectService.save(projectMapper.convertToEntity(projectDto));
        return projectMapper.convertToDto(project);
    }

    public ProjectDto update(Long id, ProjectDto projectDtoDetails) {
        Project project = projectService.update(id, projectMapper.convertToEntity(projectDtoDetails));
        return projectMapper.convertToDto(project);
    }

    public void delete(Long id) {
        projectService.delete(id);
    }
}
