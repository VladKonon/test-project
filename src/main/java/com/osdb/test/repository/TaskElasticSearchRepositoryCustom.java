package com.osdb.test.repository;

import com.osdb.test.entity.elasticsearch.ElasticSearchTask;

import java.util.List;

public interface TaskElasticSearchRepositoryCustom {

    List<ElasticSearchTask> fullTextSearch(String search);
}
