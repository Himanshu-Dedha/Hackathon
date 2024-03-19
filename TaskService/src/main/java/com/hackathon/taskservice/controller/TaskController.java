package com.hackathon.taskservice.controller;
import com.hackathon.taskservice.dto.TaskDto;
import com.hackathon.taskservice.entity.Task;
import com.hackathon.taskservice.service.TaskService;
import com.hackathon.taskservice.utils.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    private final TaskService taskService;
    private final JwtService jwtService;

    public TaskController(TaskService taskService, JwtService jwtService) {
        this.taskService = taskService;
        this.jwtService = jwtService;
    }


    @PostMapping
    public ResponseEntity<?> createTask(@RequestHeader("Authorization") String token, @RequestParam("listId") Long listId ,@RequestBody TaskDto taskDto) {
        Long ownerId = jwtService.getUserIdFromToken(token);
        if (!taskService.isUserAuthorized(ownerId, listId)) {
            return new ResponseEntity<>("User is not authorized to modify tasks in this list", HttpStatus.FORBIDDEN);
        }
        Task createdTask = taskService.createTask(taskDto, ownerId, listId);
        logger.info("The created Task is {}",createdTask);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    // Update an existing task by ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@RequestHeader("Authorization") String token,
                                           @RequestParam("listId") Long listId,
                                           @PathVariable Long id, @RequestBody TaskDto taskDto) {
        Long ownerId = jwtService.getUserIdFromToken(token);
        if (!taskService.isUserAuthorized(ownerId, listId)) {
            return new ResponseEntity<>("User is not authorized to modify tasks in this list", HttpStatus.FORBIDDEN);
        }
        return taskService.updateTask(id, taskDto, ownerId, listId)
                .map(updatedTask -> new ResponseEntity<>(updatedTask, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Get a task by ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get all tasks by owner ID
    @GetMapping("/owner")
    public ResponseEntity<List<Task>> getTasksByOwnerId(@RequestHeader("Authorization") String token) {
        Long ownerId = jwtService.getUserIdFromToken(token);
        List<Task> tasks = taskService.getTasksByOwnerId(ownerId);
        return ResponseEntity.ok(tasks);
    }

    // Get all tasks by list ID
    @GetMapping("/list/{listId}")
    public ResponseEntity<List<Task>> getTasksByListId(@PathVariable Long listId) {
        List<Task> tasks = taskService.getTasksByListId(listId);
        return ResponseEntity.ok(tasks);
    }

    // Delete a task by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@RequestHeader("Authorization") String token, @RequestParam("listId") Long listId,@PathVariable Long id) {
        Long ownerId = jwtService.getUserIdFromToken(token);
        if (!taskService.isUserAuthorized(ownerId, listId)) {
            return new ResponseEntity<>("User is not authorized to modify tasks in this list", HttpStatus.FORBIDDEN);
        }
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

}
