package com.osdb.test.repository;

import com.osdb.test.entity.elasticsearch.ElasticSearchProject;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectElasticSearchRepositoryCustom {

    List<ElasticSearchProject> fullTextSearch(String search);
}
