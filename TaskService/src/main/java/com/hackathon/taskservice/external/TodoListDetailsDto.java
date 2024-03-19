package com.hackathon.taskservice.external;

import lombok.Data;

import java.util.Set;
@Data
public class TodoListDetailsDto {
    private Long ownerId;
    private Set<Long> collaborators;
}
