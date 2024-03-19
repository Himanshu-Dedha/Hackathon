package com.hackathon.taskservice.mapper;


import com.hackathon.taskservice.dto.TaskDto;
import com.hackathon.taskservice.entity.Task;

import java.time.LocalDateTime;

public class TaskMapper {

    // Convert from Task entity to TaskDto
    public static TaskDto toDto(Task task) {
        if (task == null) {
            return null;
        }

        TaskDto dto = new TaskDto();
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setPriority(task.getPriority());
        dto.setDeadline(task.getDeadline());
        return dto;
    }

    // Convert from TaskDto to Task entity
    public static Task toEntity(TaskDto dto) {
        if (dto == null) {
            return null;
        }

        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());
        task.setPriority(dto.getPriority());
        task.setDeadline(dto.getDeadline());
        task.setUpdatedAt(LocalDateTime.now());
        task.setCreatedAt(LocalDateTime.now());
        return task;
    }
}