package com.osdb.test.service.impl;

import com.osdb.test.entity.jpa.TaskPriority;
import com.osdb.test.repository.TaskPriorityRepository;
import com.osdb.test.service.TaskPriorityService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TaskPriorityServiceImpl implements TaskPriorityService {

    TaskPriorityRepository taskPriorityRepository;

    @Override
    public List<TaskPriority> getAll() {
        return taskPriorityRepository.findAll();
    }
}
