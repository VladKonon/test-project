package com.osdb.test.entity.elasticsearch;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "project", type = "project")
@Data
public class ElasticSearchProject {

    @Id
    Long id;

    String name;

    String description;
}
