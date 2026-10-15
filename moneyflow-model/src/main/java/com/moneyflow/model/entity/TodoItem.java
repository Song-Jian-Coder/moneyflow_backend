package com.moneyflow.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 待办事项实体
 */
@Data
@TableName("todo_item")
public class TodoItem {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String title;

    private String description;

    private Boolean completed;

    private Integer priority;

    private LocalDateTime dueDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
