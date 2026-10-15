package com.moneyflow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moneyflow.common.exception.BusinessException;
import com.moneyflow.dao.mapper.TodoItemMapper;
import com.moneyflow.model.dto.*;
import com.moneyflow.model.entity.TodoItem;
import com.moneyflow.model.vo.PageResult;
import com.moneyflow.model.vo.TodoItemVO;
import com.moneyflow.service.TodoService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 待办事项 Service 实现
 */
@Service
public class TodoServiceImpl implements TodoService {

    private final TodoItemMapper todoItemMapper;

    public TodoServiceImpl(TodoItemMapper todoItemMapper) {
        this.todoItemMapper = todoItemMapper;
    }

    @Override
    public TodoItemVO create(CreateTodoRequest request) {
        TodoItem item = new TodoItem();
        item.setTitle(request.getTitle().trim());
        item.setDescription(request.getDescription());
        item.setCompleted(false);
        item.setPriority(request.getPriority() != null ? request.getPriority() : 1);
        item.setDueDate(request.getDueDate());
        item.setCreatedAt(LocalDateTime.now());
        item.setUpdatedAt(LocalDateTime.now());
        todoItemMapper.insert(item);
        return toVO(item);
    }

    @Override
    public TodoItemVO getById(Long id) {
        TodoItem item = todoItemMapper.selectById(id);
        if (item == null) {
            throw new BusinessException(404, "待办不存在");
        }
        return toVO(item);
    }

    @Override
    public TodoItemVO update(Long id, UpdateTodoRequest request) {
        TodoItem item = todoItemMapper.selectById(id);
        if (item == null) {
            throw new BusinessException(404, "待办不存在");
        }
        if (request.getTitle() != null) {
            item.setTitle(request.getTitle().trim());
        }
        if (request.getDescription() != null) {
            item.setDescription(request.getDescription());
        }
        if (request.getPriority() != null) {
            item.setPriority(request.getPriority());
        }
        if (request.getDueDate() != null) {
            item.setDueDate(request.getDueDate());
        }
        item.setUpdatedAt(LocalDateTime.now());
        todoItemMapper.updateById(item);
        return toVO(item);
    }

    @Override
    public void delete(Long id) {
        TodoItem item = todoItemMapper.selectById(id);
        if (item == null) {
            throw new BusinessException(404, "待办不存在");
        }
        todoItemMapper.deleteById(id);
    }

    @Override
    public TodoItemVO toggle(Long id) {
        TodoItem item = todoItemMapper.selectById(id);
        if (item == null) {
            throw new BusinessException(404, "待办不存在");
        }
        item.setCompleted(!item.getCompleted());
        item.setUpdatedAt(LocalDateTime.now());
        todoItemMapper.updateById(item);
        return toVO(item);
    }

    @Override
    public PageResult<TodoItemVO> list(TodoQueryRequest query) {
        // 校验分页参数
        if (query.getPageNum() == null || query.getPageNum() < 1) {
            throw new BusinessException(400, "页码必须大于0");
        }
        if (query.getPageSize() == null || query.getPageSize() < 1) {
            throw new BusinessException(400, "每页条数必须大于0");
        }
        if (query.getPageSize() > 100) {
            throw new BusinessException(400, "每页条数不能超过100");
        }

        // 校验排序字段
        String sortBy = query.getSortBy() != null ? query.getSortBy() : "createdAt";
        if (!List.of("createdAt", "dueDate", "priority").contains(sortBy)) {
            throw new BusinessException(400, "排序字段不合法");
        }

        Page<TodoItem> page = new Page<>(query.getPageNum(), query.getPageSize());
        LambdaQueryWrapper<TodoItem> wrapper = new LambdaQueryWrapper<>();

        if (query.getCompleted() != null) {
            wrapper.eq(TodoItem::getCompleted, query.getCompleted());
        }
        if (query.getPriority() != null) {
            wrapper.eq(TodoItem::getPriority, query.getPriority());
        }

        boolean isAsc = "asc".equalsIgnoreCase(query.getSortOrder());
        switch (sortBy) {
            case "dueDate" -> wrapper.orderBy(true, isAsc, TodoItem::getDueDate);
            case "priority" -> wrapper.orderBy(true, isAsc, TodoItem::getPriority);
            default -> wrapper.orderBy(true, isAsc, TodoItem::getCreatedAt);
        }

        Page<TodoItem> result = todoItemMapper.selectPage(page, wrapper);
        return PageResult.from(result, this::toVO);
    }

    @Override
    public BatchResult batchComplete(BatchTodoRequest request) {
        List<Long> successIds = new ArrayList<>();
        List<Long> failedIds = new ArrayList<>();
        for (Long id : request.getIds()) {
            TodoItem item = todoItemMapper.selectById(id);
            if (item == null) {
                failedIds.add(id);
            } else {
                item.setCompleted(true);
                item.setUpdatedAt(LocalDateTime.now());
                todoItemMapper.updateById(item);
                successIds.add(id);
            }
        }
        return new BatchResult(successIds, failedIds);
    }

    @Override
    public BatchResult batchDelete(BatchTodoRequest request) {
        // 检查重复 ID
        if (new HashSet<>(request.getIds()).size() != request.getIds().size()) {
            throw new BusinessException(400, "ID 列表包含重复值");
        }
        List<Long> successIds = new ArrayList<>();
        List<Long> failedIds = new ArrayList<>();
        for (Long id : request.getIds()) {
            TodoItem item = todoItemMapper.selectById(id);
            if (item == null) {
                failedIds.add(id);
            } else {
                todoItemMapper.deleteById(id);
                successIds.add(id);
            }
        }
        return new BatchResult(successIds, failedIds);
    }

    private TodoItemVO toVO(TodoItem item) {
        TodoItemVO vo = new TodoItemVO();
        vo.setId(item.getId());
        vo.setTitle(item.getTitle());
        vo.setDescription(item.getDescription());
        vo.setCompleted(item.getCompleted());
        vo.setPriority(item.getPriority());
        vo.setDueDate(item.getDueDate());
        vo.setCreatedAt(item.getCreatedAt());
        vo.setUpdatedAt(item.getUpdatedAt());
        return vo;
    }
}
