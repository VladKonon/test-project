package com.osdb.test.entity.jpa;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "task_priority")
@Data
public class TaskPriority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
}
