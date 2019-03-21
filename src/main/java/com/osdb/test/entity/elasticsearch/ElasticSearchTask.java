package com.osdb.test.entity.elasticsearch;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "task", type = "task")
@Data
public class ElasticSearchTask {

    @Id
    Long id;

    String name;

    String description;

    Long projectId;
}
