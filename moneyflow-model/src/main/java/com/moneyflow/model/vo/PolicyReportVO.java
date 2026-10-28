package com.moneyflow.model.vo;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 政策报告视图对象
 */
@Data
public class PolicyReportVO {

    private Long id;
    private String title;
    private String summary;
    private String category;
    private String source;
    private LocalDate publishDate;
    private String sourceUrl;
    private String documentUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
