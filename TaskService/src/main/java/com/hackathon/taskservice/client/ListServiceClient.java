package com.hackathon.taskservice.client;

import com.hackathon.taskservice.external.TodoListDetailsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@FeignClient(name = "LIST-SERVICE")
public interface ListServiceClient {
    @GetMapping("/api/lists/{listId}")
    ResponseEntity<TodoListDetailsDto> getListDetails(@PathVariable("listId") Long listId);
}
