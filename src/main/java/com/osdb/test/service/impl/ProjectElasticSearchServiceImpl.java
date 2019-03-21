package com.osdb.test.service.impl;

import com.osdb.test.entity.elasticsearch.ElasticSearchProject;
import com.osdb.test.repository.ProjectElasticSearchRepository;
import com.osdb.test.repository.ProjectElasticSearchRepositoryCustom;
import com.osdb.test.service.ProjectElasticSearchService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProjectElasticSearchServiceImpl implements ProjectElasticSearchService {

    ProjectElasticSearchRepository projectElasticSearchRepository;
    ProjectElasticSearchRepositoryCustom projectElasticSearchRepositoryCustom;

    @Override
    public List<ElasticSearchProject> fullTextSearch(String search) {
        return projectElasticSearchRepositoryCustom.fullTextSearch(search);
    }

    @Override
    public List<ElasticSearchProject> findByName(String name) {
        return projectElasticSearchRepository.findByName(name);
    }

    @Override
    public void save(ElasticSearchProject esProject) {
        projectElasticSearchRepository.save(esProject);
    }

    @Override
    public void delete(Long id) {
        projectElasticSearchRepository.deleteById(id);
    }
}
