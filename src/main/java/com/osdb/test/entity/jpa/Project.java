package com.osdb.test.entity.jpa;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "project")
@EntityListeners(AuditingEntityListener.class)
@Data
public class Project {

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
}
