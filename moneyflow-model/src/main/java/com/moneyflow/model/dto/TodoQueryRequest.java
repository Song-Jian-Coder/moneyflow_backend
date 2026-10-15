package com.moneyflow.model.dto;

import lombok.Data;

/**
 * 待办列表查询请求
 */
@Data
public class TodoQueryRequest {

    private Boolean completed;

    private Integer priority;

    private String sortBy = "createdAt";

    private String sortOrder = "desc";

    private Integer pageNum = 1;

    private Integer pageSize = 20;
}
