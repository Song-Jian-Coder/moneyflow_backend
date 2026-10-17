package com.moneyflow.service.impl;

import com.moneyflow.common.exception.BusinessException;
import com.moneyflow.dao.repository.TodoItemRepository;
import com.moneyflow.model.dto.*;
import com.moneyflow.model.entity.TodoItem;
import com.moneyflow.model.vo.PageResult;
import com.moneyflow.model.vo.TodoItemVO;
import com.moneyflow.service.TodoService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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

    private final TodoItemRepository todoItemRepository;

    public TodoServiceImpl(TodoItemRepository todoItemRepository) {
        this.todoItemRepository = todoItemRepository;
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
        todoItemRepository.save(item);
        return toVO(item);
    }

    @Override
    public TodoItemVO getById(Long id) {
        TodoItem item = todoItemRepository.findById(id)
                .orElseThrow(() -> new BusinessException(404, "待办不存在"));
        return toVO(item);
    }

    @Override
    public TodoItemVO update(Long id, UpdateTodoRequest request) {
        TodoItem item = todoItemRepository.findById(id)
                .orElseThrow(() -> new BusinessException(404, "待办不存在"));
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
        todoItemRepository.save(item);
        return toVO(item);
    }

    @Override
    public void delete(Long id) {
        TodoItem item = todoItemRepository.findById(id)
                .orElseThrow(() -> new BusinessException(404, "待办不存在"));
        todoItemRepository.deleteById(id);
    }

    @Override
    public TodoItemVO toggle(Long id) {
        TodoItem item = todoItemRepository.findById(id)
                .orElseThrow(() -> new BusinessException(404, "待办不存在"));
        item.setCompleted(!item.getCompleted());
        item.setUpdatedAt(LocalDateTime.now());
        todoItemRepository.save(item);
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

        // 构建动态查询条件
        Specification<TodoItem> spec = (root, criteriaQuery, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (query.getCompleted() != null) {
                predicates.add(cb.equal(root.get("completed"), query.getCompleted()));
            }
            if (query.getPriority() != null) {
                predicates.add(cb.equal(root.get("priority"), query.getPriority()));
            }
            if (predicates.isEmpty()) {
                return cb.conjunction();
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 构建排序：使用 Java 属性名（Spring Data Sort 按属性路径解析）
        boolean isAsc = "asc".equalsIgnoreCase(query.getSortOrder());
        Sort sort = isAsc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        // Spring Data 分页从 0 开始
        PageRequest pageRequest = PageRequest.of(query.getPageNum() - 1, query.getPageSize(), sort);

        Page<TodoItem> result = todoItemRepository.findAll(spec, pageRequest);
        return PageResult.from(result, this::toVO);
    }

    @Override
    public BatchResult batchComplete(BatchTodoRequest request) {
        List<Long> successIds = new ArrayList<>();
        List<Long> failedIds = new ArrayList<>();
        for (Long id : request.getIds()) {
            TodoItem item = todoItemRepository.findById(id).orElse(null);
            if (item == null) {
                failedIds.add(id);
            } else {
                item.setCompleted(true);
                item.setUpdatedAt(LocalDateTime.now());
                todoItemRepository.save(item);
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
            TodoItem item = todoItemRepository.findById(id).orElse(null);
            if (item == null) {
                failedIds.add(id);
            } else {
                todoItemRepository.deleteById(id);
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
