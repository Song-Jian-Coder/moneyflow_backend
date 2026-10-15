package com.moneyflow.web.controller;

import com.moneyflow.common.exception.BusinessException;
import com.moneyflow.model.dto.*;
import com.moneyflow.model.result.Result;
import com.moneyflow.model.vo.PageResult;
import com.moneyflow.model.vo.TodoItemVO;
import com.moneyflow.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 待办事项 Controller
 */
@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public Result<TodoItemVO> create(@Valid @RequestBody CreateTodoRequest request) {
        return Result.success(todoService.create(request));
    }

    @GetMapping
    public Result<PageResult<TodoItemVO>> list(TodoQueryRequest query) {
        return Result.success(todoService.list(query));
    }

    @GetMapping("/{id}")
    public Result<TodoItemVO> getById(@PathVariable Long id) {
        validateId(id);
        return Result.success(todoService.getById(id));
    }

    @PutMapping("/{id}")
    public Result<TodoItemVO> update(@PathVariable Long id,
                                     @Valid @RequestBody UpdateTodoRequest request) {
        validateId(id);
        // 检查请求体是否全为空
        if (request.getTitle() == null && request.getDescription() == null
                && request.getPriority() == null && request.getDueDate() == null) {
            throw new BusinessException(400, "至少需要提供一个更新字段");
        }
        return Result.success(todoService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        validateId(id);
        todoService.delete(id);
        return Result.success(null);
    }

    @PutMapping("/{id}/toggle")
    public Result<TodoItemVO> toggle(@PathVariable Long id) {
        validateId(id);
        return Result.success(todoService.toggle(id));
    }

    @PutMapping("/batch/complete")
    public Result<?> batchComplete(@Valid @RequestBody BatchTodoRequest request) {
        TodoService.BatchResult result = todoService.batchComplete(request);
        return buildBatchResponse(result, "已标记完成");
    }

    @DeleteMapping("/batch")
    public Result<?> batchDelete(@Valid @RequestBody BatchTodoRequest request) {
        TodoService.BatchResult result = todoService.batchDelete(request);
        return buildBatchResponse(result, "已删除");
    }

    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(400, "ID 必须大于0");
        }
    }

    private Result<?> buildBatchResponse(TodoService.BatchResult result, String action) {
        if (result.failedIds().isEmpty()) {
            return Result.success(null);
        }
        if (result.successIds().isEmpty()) {
            return Result.error(404, result.failedIds().size() + "条待办不存在");
        }
        // 部分成功
        return Result.error(207,
                result.failedIds().size() + "条待办不存在，其余" + action);
    }
}
