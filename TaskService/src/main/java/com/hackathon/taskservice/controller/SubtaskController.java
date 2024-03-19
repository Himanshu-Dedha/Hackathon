package com.hackathon.taskservice.controller;

import com.hackathon.taskservice.entity.Subtask;
import com.hackathon.taskservice.service.SubtaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subtasks")
public class SubtaskController {

    private final SubtaskService subtaskService;

    @Autowired
    public SubtaskController(SubtaskService subtaskService) {
        this.subtaskService = subtaskService;
    }

    @PostMapping
    public ResponseEntity<Subtask> createSubtask(@RequestBody Subtask subtask) {
        Subtask createdSubtask = subtaskService.createSubtask(subtask);
        return new ResponseEntity<>(createdSubtask, HttpStatus.CREATED);
    }

    @PutMapping("/{subtaskId}")
    public ResponseEntity<Subtask> updateSubtask(@PathVariable Long subtaskId, @RequestBody Subtask subtask) {
        return subtaskService.updateSubtask(subtaskId, subtask)
                .map(updatedSubtask -> ResponseEntity.ok(updatedSubtask))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{subtaskId}")
    public ResponseEntity<Subtask> getSubtaskById(@PathVariable Long subtaskId) {
        return subtaskService.getSubtaskById(subtaskId)
                .map(subtask -> ResponseEntity.ok(subtask))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<Subtask>> getSubtasksByParentTaskId(@PathVariable Long taskId) {
        List<Subtask> subtasks = subtaskService.getSubtasksByParentTaskId(taskId);
        return ResponseEntity.ok(subtasks);
    }

    @DeleteMapping("/{subtaskId}")
    public ResponseEntity<Void> deleteSubtask(@PathVariable Long subtaskId) {
        subtaskService.deleteSubtask(subtaskId);
        return ResponseEntity.noContent().build();
    }
}
