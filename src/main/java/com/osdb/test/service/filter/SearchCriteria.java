package com.osdb.test.service.filter;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SearchCriteria {

    String key;
    String operation;
    String value;
}
