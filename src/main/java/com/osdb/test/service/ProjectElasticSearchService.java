package com.osdb.test.service;

import com.osdb.test.entity.elasticsearch.ElasticSearchProject;

import java.util.List;

public interface ProjectElasticSearchService {

    List<ElasticSearchProject> fullTextSearch(String search);

    List<ElasticSearchProject> findByName(String name);

    void save(ElasticSearchProject esProject);

    void delete(Long id);
}
