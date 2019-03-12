package com.osdb.test.repository;

import com.osdb.test.entity.TaskPriority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskPriorityRepository extends JpaRepository<TaskPriority, Long> {

}
