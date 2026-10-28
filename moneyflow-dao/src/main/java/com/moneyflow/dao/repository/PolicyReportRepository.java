package com.moneyflow.dao.repository;

import com.moneyflow.model.entity.PolicyReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 政策报告数据访问层
 */
@Repository
public interface PolicyReportRepository extends JpaRepository<PolicyReport, Long> {

    /**
     * 按分类分页查询，按发布日期降序
     */
    Page<PolicyReport> findByCategoryOrderByPublishDateDesc(String category, Pageable pageable);

    /**
     * 分页查询所有报告，按发布日期降序
     */
    Page<PolicyReport> findAllByOrderByPublishDateDesc(Pageable pageable);

    /**
     * 根据标题和来源判断是否已存在（用于爬取去重）
     */
    boolean existsByTitleAndSource(String title, String source);
}
