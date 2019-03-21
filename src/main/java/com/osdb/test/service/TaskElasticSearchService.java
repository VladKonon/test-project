package com.osdb.test.service;

import com.osdb.test.entity.elasticsearch.ElasticSearchTask;

import java.util.List;

public interface TaskElasticSearchService {

    List<ElasticSearchTask> fullTextSearch(String search);

    List<ElasticSearchTask> findByName(String name);

    void save(ElasticSearchTask esTask);

    void delete(Long id );
}
