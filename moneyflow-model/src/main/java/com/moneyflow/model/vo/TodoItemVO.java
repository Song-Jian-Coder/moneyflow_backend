package com.moneyflow.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 待办事项响应 VO
 */
@Data
public class TodoItemVO {

    private Long id;
    private String title;
    private String description;
    private Boolean completed;
    private Integer priority;
    private LocalDateTime dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
