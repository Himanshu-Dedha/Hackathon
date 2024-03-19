package com.hackathon.taskservice.service;

import com.hackathon.taskservice.client.ListServiceClient;
import com.hackathon.taskservice.dto.TaskDto;
import com.hackathon.taskservice.entity.Task;
import com.hackathon.taskservice.external.TodoListDetailsDto;
import com.hackathon.taskservice.repository.SubtaskRepository;
import com.hackathon.taskservice.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.hackathon.taskservice.mapper.TaskMapper.toEntity;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private ListServiceClient listServiceClient;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;

    }
    public boolean isUserAuthorized(Long userId, Long listId) {
        ResponseEntity<TodoListDetailsDto> response = listServiceClient.getListDetails(listId);
        if (response.getStatusCode() == HttpStatus.OK) {
            TodoListDetailsDto listDetails = response.getBody();
            assert listDetails != null;
            return listDetails.getOwnerId().equals(userId) || listDetails.getCollaborators().contains(userId);
        }
        return false;
    }

    public Task createTask(TaskDto taskDto, Long ownerId, Long listId) {
        Task task = toEntity(taskDto);
        task.setOwnerId(ownerId);
        task.setListId(listId);
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    public Optional<Task> updateTask(Long id, TaskDto updatedTaskDto, Long ownerId, Long listId) {
        Task task1 = toEntity(updatedTaskDto);
        task1.setOwnerId(ownerId);
        task1.setListId(listId);
        return taskRepository.findById(id).map(task -> {
            task.setTitle(task1.getTitle());
            task.setDescription(task1.getDescription());
            task.setStatus(task1.getStatus());
            task.setPriority(task1.getPriority());
            task.setDeadline(task1.getDeadline());
            task.setUpdatedAt(LocalDateTime.now());
            return taskRepository.save(task);
        });
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public List<Task> getTasksByOwnerId(Long ownerId) {
        return taskRepository.findByOwnerId(ownerId);
    }

    public List<Task> getTasksByListId(Long listId) {
        return taskRepository.findByListId(listId);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

}