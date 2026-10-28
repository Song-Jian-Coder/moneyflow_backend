package com.moneyflow.model.dto;

import lombok.Data;

/**
 * 政策报告查询请求
 */
@Data
public class PolicyReportQueryRequest {

    private Integer page = 1;

    private Integer size = 20;

    private String category;
}
