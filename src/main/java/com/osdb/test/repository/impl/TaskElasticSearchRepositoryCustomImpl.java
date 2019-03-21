package com.osdb.test.repository.impl;

import com.osdb.test.entity.elasticsearch.ElasticSearchTask;
import com.osdb.test.repository.TaskElasticSearchRepositoryCustom;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

@Repository
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TaskElasticSearchRepositoryCustomImpl implements TaskElasticSearchRepositoryCustom {

    ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public List<ElasticSearchTask> fullTextSearch(String search) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(multiMatchQuery(search).field("name").field("description")
                        .type(MultiMatchQueryBuilder.Type.BEST_FIELDS)).build();

        return elasticsearchTemplate.queryForList(searchQuery, ElasticSearchTask.class);
    }
}
