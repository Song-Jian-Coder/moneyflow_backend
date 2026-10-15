package com.moneyflow.service;

import com.moneyflow.model.dto.*;
import com.moneyflow.model.vo.PageResult;
import com.moneyflow.model.vo.TodoItemVO;

/**
 * 待办事项 Service 接口
 */
public interface TodoService {

    TodoItemVO create(CreateTodoRequest request);

    TodoItemVO getById(Long id);

    TodoItemVO update(Long id, UpdateTodoRequest request);

    void delete(Long id);

    TodoItemVO toggle(Long id);

    PageResult<TodoItemVO> list(TodoQueryRequest query);

    BatchResult batchComplete(BatchTodoRequest request);

    BatchResult batchDelete(BatchTodoRequest request);

    /**
     * 批量操作结果
     */
    record BatchResult(java.util.List<Long> successIds, java.util.List<Long> failedIds) {
    }
}
