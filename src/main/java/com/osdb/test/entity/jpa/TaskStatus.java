package com.osdb.test.entity.jpa;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "task_status")
@Data
public class TaskStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    String name;
}
