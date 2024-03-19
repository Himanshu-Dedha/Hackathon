package com.hackathon.listservice.mapper;

import com.hackathon.listservice.dto.ListDto;
import com.hackathon.listservice.entity.TodoList;

import java.time.LocalDateTime;

public class TodoListMapper {

    public static ListDto entityToListDto(TodoList todoList) {
        ListDto dto = new ListDto();
        dto.setTitle(todoList.getTitle());
        return dto;
    }

    public static TodoList listDtoToEntity(ListDto dto) {
        TodoList todoList = new TodoList();
        todoList.setTitle(dto.getTitle());
        todoList.setOwnerId(null);
        todoList.setCreatedAt(LocalDateTime.now());
        todoList.setLastUpdatedAt(LocalDateTime.now());
        return todoList;
    }
}