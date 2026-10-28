package com.moneyflow.service.impl;

import com.moneyflow.common.exception.BusinessException;
import com.moneyflow.dao.repository.PolicyReportRepository;
import com.moneyflow.model.dto.PolicyReportQueryRequest;
import com.moneyflow.model.entity.PolicyReport;
import com.moneyflow.model.vo.PageResult;
import com.moneyflow.model.vo.PolicyReportVO;
import com.moneyflow.service.PolicyReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * 政策报告服务实现
 */
@Service
@RequiredArgsConstructor
public class PolicyReportServiceImpl implements PolicyReportService {

    private final PolicyReportRepository policyReportRepository;

    @Override
    public PageResult<PolicyReportVO> queryReports(PolicyReportQueryRequest request) {
        int page = request.getPage() != null ? request.getPage() - 1 : 0;
        int size = request.getSize() != null ? request.getSize() : 20;
        Pageable pageable = PageRequest.of(page, size);

        Page<PolicyReport> reportPage;
        if (request.getCategory() != null && !request.getCategory().isBlank()) {
            reportPage = policyReportRepository.findByCategoryOrderByPublishDateDesc(request.getCategory(), pageable);
        } else {
            reportPage = policyReportRepository.findAllByOrderByPublishDateDesc(pageable);
        }

        return PageResult.from(reportPage, this::toVO);
    }

    @Override
    public PolicyReportVO getReportById(Long id) {
        PolicyReport report = policyReportRepository.findById(id)
                .orElseThrow(() -> new BusinessException(404, "政策报告不存在"));
        return toVO(report);
    }

    private PolicyReportVO toVO(PolicyReport report) {
        PolicyReportVO vo = new PolicyReportVO();
        vo.setId(report.getId());
        vo.setTitle(report.getTitle());
        vo.setSummary(report.getSummary());
        vo.setCategory(report.getCategory());
        vo.setSource(report.getSource());
        vo.setPublishDate(report.getPublishDate());
        vo.setSourceUrl(report.getSourceUrl());
        vo.setDocumentUrl(report.getDocumentUrl());
        vo.setCreatedAt(report.getCreatedAt());
        vo.setUpdatedAt(report.getUpdatedAt());
        return vo;
    }
}
