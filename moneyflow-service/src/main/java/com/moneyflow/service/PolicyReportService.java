package com.moneyflow.service;

import com.moneyflow.model.dto.PolicyReportQueryRequest;
import com.moneyflow.model.vo.PageResult;
import com.moneyflow.model.vo.PolicyReportVO;

/**
 * 政策报告服务接口
 */
public interface PolicyReportService {

    /**
     * 分页查询政策报告列表
     */
    PageResult<PolicyReportVO> queryReports(PolicyReportQueryRequest request);

    /**
     * 根据 ID 查询报告详情
     */
    PolicyReportVO getReportById(Long id);
}
