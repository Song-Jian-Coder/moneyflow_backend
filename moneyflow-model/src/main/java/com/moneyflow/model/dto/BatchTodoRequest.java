package com.moneyflow.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

/**
 * 批量操作请求
 */
@Data
public class BatchTodoRequest {

    @NotEmpty(message = "ID 列表不能为空")
    @Size(max = 100, message = "批量操作不能超过100条")
    private List<@Positive(message = "ID 必须大于0") Long> ids;
}
