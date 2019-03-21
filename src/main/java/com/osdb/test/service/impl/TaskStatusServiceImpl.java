package com.osdb.test.service.impl;

import com.osdb.test.entity.jpa.TaskStatus;
import com.osdb.test.repository.TaskStatusRepository;
import com.osdb.test.service.TaskStatusService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TaskStatusServiceImpl implements TaskStatusService {

    TaskStatusRepository taskStatusRepository;

    @Override
    public List<TaskStatus> getAll() {
        return taskStatusRepository.findAll();
    }
}
