package com.osdb.test.entity.jpa;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "task")
@EntityListeners(AuditingEntityListener.class)
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    String name;

    @Column
    String description;

    @Column(name = "created_at")
    @CreatedDate
    Instant createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    Instant updatedAt;

    @Column(name = "project_id")
    Long projectId;

    @Column(name = "priority_id")
    Long priorityId;

    @Column(name = "status_id")
    Long statusId;

    @Column(name = "start_date")
    Instant startDate;


}
