package com.moneyflow.web.controller;

import com.moneyflow.model.dto.PolicyReportQueryRequest;
import com.moneyflow.model.result.Result;
import com.moneyflow.model.vo.PageResult;
import com.moneyflow.model.vo.PolicyReportVO;
import com.moneyflow.service.PolicyReportCrawlerService;
import com.moneyflow.service.PolicyReportService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 政策报告 Controller
 */
@RestController
@RequestMapping("/api/policy-reports")
public class PolicyReportController {

    private final PolicyReportService policyReportService;
    private final PolicyReportCrawlerService crawlerService;

    public PolicyReportController(PolicyReportService policyReportService,
                                  PolicyReportCrawlerService crawlerService) {
        this.policyReportService = policyReportService;
        this.crawlerService = crawlerService;
    }

    /**
     * 分页查询政策报告列表
     */
    @GetMapping
    public Result<PageResult<PolicyReportVO>> list(PolicyReportQueryRequest request) {
        return Result.success(policyReportService.queryReports(request));
    }

    /**
     * 查询报告详情
     */
    @GetMapping("/{id}")
    public Result<PolicyReportVO> getById(@PathVariable Long id) {
        return Result.success(policyReportService.getReportById(id));
    }

    /**
     * 手动触发爬取
     */
    @PostMapping("/crawl")
    public Result<Map<String, Object>> crawl() {
        return Result.success(crawlerService.crawl());
    }
}
