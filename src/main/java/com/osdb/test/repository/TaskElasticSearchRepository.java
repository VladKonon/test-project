package com.osdb.test.repository;

import com.osdb.test.entity.elasticsearch.ElasticSearchTask;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface TaskElasticSearchRepository extends ElasticsearchRepository<ElasticSearchTask, Long> {

    List<ElasticSearchTask> findByName(String name);
}
