package com.moneyflow.dao.repository;

import com.moneyflow.model.entity.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 待办事项 Repository
 */
public interface TodoItemRepository extends JpaRepository<TodoItem, Long>,
                                            JpaSpecificationExecutor<TodoItem> {
}
