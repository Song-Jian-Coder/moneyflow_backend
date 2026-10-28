package com.moneyflow.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 政策报告实体
 */
@Data
@Entity
@Table(name = "policy_report", indexes = {
    @Index(name = "idx_category", columnList = "category"),
    @Index(name = "idx_publish_date", columnList = "publish_date"),
    @Index(name = "idx_created_at", columnList = "created_at")
})
public class PolicyReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(length = 2000)
    private String summary;

    @Column(nullable = false, length = 50)
    private String category;

    @Column(length = 100)
    private String source;

    @Column(name = "publish_date")
    private LocalDate publishDate;

    @Column(name = "source_url", length = 500)
    private String sourceUrl;

    @Column(name = "document_url", length = 500)
    private String documentUrl;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
