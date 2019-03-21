package com.osdb.test.service.impl;

import com.osdb.test.entity.elasticsearch.ElasticSearchTask;
import com.osdb.test.repository.TaskElasticSearchRepository;
import com.osdb.test.repository.TaskElasticSearchRepositoryCustom;
import com.osdb.test.service.TaskElasticSearchService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TaskElasticSearchServiceImpl implements TaskElasticSearchService {

    TaskElasticSearchRepository taskElasticSearchRepository;
    TaskElasticSearchRepositoryCustom taskElasticSearchRepositoryCustom;

    public List<ElasticSearchTask> fullTextSearch(String search) {
        return taskElasticSearchRepositoryCustom.fullTextSearch(search);
    }

    @Override
    public List<ElasticSearchTask> findByName(String name) {
        return taskElasticSearchRepository.findByName(name);
    }

    @Override
    public void save(ElasticSearchTask esTask) {
        taskElasticSearchRepository.save(esTask);
    }

    @Override
    public void delete(Long id) {
        taskElasticSearchRepository.deleteById(id);
    }
}
