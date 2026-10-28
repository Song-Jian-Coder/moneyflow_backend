package com.moneyflow.service;

import java.util.Map;

/**
 * 政策报告爬取服务接口
 */
public interface PolicyReportCrawlerService {

    /**
     * 手动触发爬取
     * @return 爬取结果统计
     */
    Map<String, Object> crawl();
}
