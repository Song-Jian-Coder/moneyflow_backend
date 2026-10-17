package com.moneyflow.model.vo;

import lombok.Data;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 通用分页响应
 */
@Data
public class PageResult<T> {

    private List<T> records;
    private long total;
    private int pageNum;
    private int pageSize;
    private int totalPages;

    public static <S, T> PageResult<T> from(
            org.springframework.data.domain.Page<S> page,
            Function<S, T> converter) {
        PageResult<T> result = new PageResult<>();
        result.setRecords(page.getContent().stream().map(converter).collect(Collectors.toList()));
        result.setTotal(page.getTotalElements());
        result.setPageNum(page.getNumber() + 1);  // Spring Data 0-based → 1-based
        result.setPageSize(page.getSize());
        result.setTotalPages(page.getTotalPages());
        return result;
    }
}
