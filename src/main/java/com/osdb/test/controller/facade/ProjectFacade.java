package com.osdb.test.controller.facade;

import com.osdb.test.controller.mapper.ProjectMapper;
import com.osdb.test.controller.dto.ProjectDto;
import com.osdb.test.entity.elasticsearch.ElasticSearchProject;
import com.osdb.test.entity.jpa.Project;
import com.osdb.test.exception.NotFoundException;
import com.osdb.test.service.ProjectElasticSearchService;
import com.osdb.test.service.ProjectService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProjectFacade {

    ProjectService projectService;
    ProjectElasticSearchService projectElasticSearchService;
    ProjectMapper projectMapper;

    public List<ElasticSearchProject> fullTextSearch(String search) {
        return projectElasticSearchService.fullTextSearch(search);
    }

    public List<ElasticSearchProject> getByName(String name) {
        return projectElasticSearchService.findByName(name);
    }

    public Page<ProjectDto> getAll(Pageable pageable, String search) {
        Page<Project> page = projectService.getAll(pageable, search);
        return page.map(projectMapper::convertToDto);

    }

    public ProjectDto getById(Long id) throws NotFoundException {
        return projectMapper.convertToDto(projectService.get(id));
    }

    public ProjectDto save(ProjectDto projectDto) {
        Project project = projectService.save(projectMapper.convertToEntity(projectDto));
        projectElasticSearchService.save(projectMapper.convertToElasticSearchEntity(project));

        return projectMapper.convertToDto(project);
    }

    public ProjectDto update(Long id, ProjectDto projectDtoDetails) {
        Project project = projectService.update(id, projectMapper.convertToEntity(projectDtoDetails));
        projectElasticSearchService.save(projectMapper.convertToElasticSearchEntity(project));

        return projectMapper.convertToDto(project);
    }

    public void delete(Long id) {
        projectService.delete(id);
        projectElasticSearchService.delete(id);
    }
}
