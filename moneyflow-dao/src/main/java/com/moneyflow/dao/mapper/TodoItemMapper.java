package com.moneyflow.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moneyflow.model.entity.TodoItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * 待办事项 Mapper
 */
@Mapper
public interface TodoItemMapper extends BaseMapper<TodoItem> {
}
