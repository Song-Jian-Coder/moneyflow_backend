package com.moneyflow.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 待办事项实体
 */
@Data
@Entity
@Table(name = "todo_item", indexes = {
    @Index(name = "idx_completed", columnList = "completed"),
    @Index(name = "idx_priority", columnList = "priority"),
    @Index(name = "idx_created_at", columnList = "created_at"),
    @Index(name = "idx_due_date", columnList = "due_date")
})
public class TodoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    private Boolean completed = false;

    @Column(nullable = false)
    private Integer priority = 1;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
