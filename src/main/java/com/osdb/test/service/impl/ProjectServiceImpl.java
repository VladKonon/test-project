package com.osdb.test.service.impl;

import com.osdb.test.entity.Project;
import com.osdb.test.exception.NotFoundException;
import com.osdb.test.service.filter.ProjectSpecification;
import com.osdb.test.service.filter.SearchCriteria;
import com.osdb.test.repository.ProjectRepository;
import com.osdb.test.service.ProjectService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProjectServiceImpl implements ProjectService {

    private final static String PROJECT_NOT_FOUND = "Project not found.";

    ProjectRepository projectRepository;

    @Override
    public Page<Project> getAll(Pageable pageable, String search) {
        if (search != null) {
            Pattern pattern = Pattern.compile("(\\w+?)(:)(\\w+)");
            Matcher matcher = pattern.matcher(search);

            if (matcher.find()) {
                SearchCriteria searchCriteria = SearchCriteria.builder()
                        .key(matcher.group(1))
                        .operation(matcher.group(2))
                        .value(matcher.group(3))
                        .build();

                Specification<Project> specification = new ProjectSpecification(searchCriteria);

                return projectRepository.findAll(specification, pageable);
            }
        }

        return projectRepository.findAll(pageable);
    }

    @Override
    public Project get(Long id) throws NotFoundException {
        return projectRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(PROJECT_NOT_FOUND));
    }

    @Override
    public Project save(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project update(Long id, Project projectDetails) {
        Project project = get(id);

        project.setName(projectDetails.getName());
        project.setDescription(projectDetails.getDescription());

        return projectRepository.save(project);
    }

    @Override
    public void delete(Long id) {
        Project project = get(id);
        projectRepository.delete(project);
    }
}
