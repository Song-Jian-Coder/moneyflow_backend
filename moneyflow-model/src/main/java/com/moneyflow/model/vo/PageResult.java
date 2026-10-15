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
            com.baomidou.mybatisplus.extension.plugins.pagination.Page<S> page,
            Function<S, T> converter) {
        PageResult<T> result = new PageResult<>();
        result.setRecords(page.getRecords().stream().map(converter).collect(Collectors.toList()));
        result.setTotal(page.getTotal());
        result.setPageNum((int) page.getCurrent());
        result.setPageSize((int) page.getSize());
        result.setTotalPages((int) page.getPages());
        return result;
    }
}
