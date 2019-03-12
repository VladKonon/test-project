package com.osdb.test.service.filter;

import com.osdb.test.entity.Project;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProjectSpecification implements Specification<Project> {

    SearchCriteria criteria;

    public ProjectSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        if (criteria.getOperation().equalsIgnoreCase(":"))
            return criteriaBuilder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%");

        return null;
    }
}
