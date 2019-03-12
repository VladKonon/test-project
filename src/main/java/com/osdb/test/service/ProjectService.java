package com.osdb.test.service;

import com.osdb.test.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectService {

    Page<Project> getAll(Pageable pageable, String search);

    Project get(Long id);

    Project save(Project project);

    Project update(Long id, Project projectDetails);

    void delete(Long id);

}