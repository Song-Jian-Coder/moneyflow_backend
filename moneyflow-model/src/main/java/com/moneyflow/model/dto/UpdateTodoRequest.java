package com.moneyflow.model.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 更新待办请求（所有字段可选）
 */
@Data
public class UpdateTodoRequest {

    @Size(max = 200, message = "标题长度不能超过200个字符")
    private String title;

    @Size(max = 2000, message = "详情长度不能超过2000个字符")
    private String description;

    @Min(value = 0, message = "优先级值不合法")
    @Max(value = 2, message = "优先级值不合法")
    private Integer priority;

    @Future(message = "截止时间不能早于当前时间")
    private LocalDateTime dueDate;
}
