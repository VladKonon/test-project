package com.osdb.test.repository;

import com.osdb.test.entity.elasticsearch.ElasticSearchProject;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectElasticSearchRepository extends ElasticsearchRepository<ElasticSearchProject, Long> {

    List<ElasticSearchProject> findByName(String name);
}
