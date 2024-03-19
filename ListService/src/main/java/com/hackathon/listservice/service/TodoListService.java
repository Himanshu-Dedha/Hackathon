package com.hackathon.listservice.service;

import com.hackathon.listservice.dto.ListDto;
import com.hackathon.listservice.entity.TodoList;
import com.hackathon.listservice.repository.TodoListRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.hackathon.listservice.mapper.TodoListMapper.listDtoToEntity;

@Service
public class TodoListService {
    private final TodoListRepository todoListRepository;


    public TodoListService(TodoListRepository todoListRepository) {
        this.todoListRepository = todoListRepository;
    }

    // Create a new list
    public TodoList createList(ListDto listDto, Long ownerId) {
        TodoList todoList = listDtoToEntity(listDto);
        todoList.setOwnerId(ownerId);
        todoList.setCreatedAt(LocalDateTime.now());
        todoList.setLastUpdatedAt(LocalDateTime.now());
        return todoListRepository.save(todoList);
    }

    // Update an existing list
    public TodoList updateList(Long id, ListDto listDto, Long ownerId) {
        TodoList updatedTodoList = listDtoToEntity(listDto);
        return todoListRepository.findById(id)
                .map(todoList -> {
                    todoList.setTitle(updatedTodoList.getTitle());
                    todoList.setOwnerId(ownerId);
                    todoList.setLastUpdatedAt(LocalDateTime.now());
                    return todoListRepository.save(todoList);
                })
                .orElseGet(() -> {
                    updatedTodoList.setId(id);
                    return todoListRepository.save(updatedTodoList);
                });
    }

    // Retrieve a list by ID
    public Optional<TodoList> getListById(Long id) {
        return todoListRepository.findById(id);
    }

    // Retrieve all lists by owner ID
    public List<TodoList> getListsByOwnerId(Long ownerId) {
        return todoListRepository.findByOwnerId(ownerId);
    }

    // Delete a list by ID
    public void deleteList(Long id) {
        todoListRepository.deleteById(id);
    }

    // Retrieve all lists
    public List<TodoList> getAllLists() {
        return todoListRepository.findAll();
    }

    public void addCollaborator(Long listId, Long collaboratorId) {
        TodoList list = todoListRepository.findById(listId)
                .orElseThrow(() -> new EntityNotFoundException("List not found with id: " + listId));
        list.getCollaborators().add(collaboratorId);
        todoListRepository.save(list);
    }

    public void removeCollaborator(Long listId, Long collaboratorId) {
        TodoList list = todoListRepository.findById(listId)
                .orElseThrow(() -> new EntityNotFoundException("List not found with id: " + listId));
        list.getCollaborators().remove(collaboratorId);
        todoListRepository.save(list);
    }
    public List<TodoList> getListsAsCollaborator(Long userId) {
        return todoListRepository.findListsWhereUserIsCollaborator(userId);
    }

}