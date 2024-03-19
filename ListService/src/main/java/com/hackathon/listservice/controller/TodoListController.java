package com.hackathon.listservice.controller;

import com.hackathon.listservice.dto.CollaboratorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hackathon.listservice.dto.ListDto;
import com.hackathon.listservice.entity.TodoList;
import com.hackathon.listservice.service.TodoListService;
import com.hackathon.listservice.utils.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lists")
public class TodoListController {
    private static final Logger logger = LoggerFactory.getLogger(TodoListController.class);

    private final TodoListService todoListService;
    private final JwtService jwtService;

    public TodoListController(TodoListService todoListService, JwtService jwtService) {
        this.todoListService = todoListService;
        this.jwtService = jwtService;
    }

    // Create a new list
    @PostMapping
    public ResponseEntity<TodoList> createList(
                                        @RequestHeader("Authorization") String token,
                                        @RequestBody ListDto todoList) {
        logger.info("The token is {}", token);
        Long ownerId = jwtService.getUserIdFromToken(token);
        logger.info("Owner ID extracted from token: {}", ownerId);
        TodoList createdList = todoListService.createList(todoList,ownerId);
        return new ResponseEntity<>(createdList, HttpStatus.CREATED);
    }

    // Update an existing list
    @PutMapping("/{id}")
    public ResponseEntity<TodoList> updateList(@PathVariable Long id, @RequestBody ListDto todoList,@RequestHeader("Authorization") String token) {
        Long ownerId =  jwtService.getUserIdFromToken(token);
        TodoList updatedList = todoListService.updateList(id, todoList, ownerId);
        return ResponseEntity.ok(updatedList);
    }

    // Retrieve a specific list by ID
    @GetMapping("/{id}")
    public ResponseEntity<TodoList> getListById(@PathVariable Long id) {
        return todoListService.getListById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Retrieve all lists by owner ID
    @GetMapping("/owner")
    public ResponseEntity<List<TodoList>> getListsByOwnerId(@RequestHeader("Authorization") String token) {
        Long ownerId =  jwtService.getUserIdFromToken(token);
        List<TodoList> lists = todoListService.getListsByOwnerId(ownerId);
        return ResponseEntity.ok(lists);
    }

    // Delete a list
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteList(@PathVariable Long id) {
        todoListService.deleteList(id);
        return ResponseEntity.noContent().build();
    }

    // Retrieve all lists
    @GetMapping
    public ResponseEntity<List<TodoList>> getAllLists() {
        List<TodoList> lists = todoListService.getAllLists();
        return ResponseEntity.ok(lists);
    }

//    ADDING COLLABORATORS

    @PostMapping("/{listId}/collaborators")
    public ResponseEntity<?> addCollaborator(@PathVariable Long listId,
                                             @RequestBody CollaboratorDto collaboratorId,
                                             @RequestHeader("Authorization") String token) {
        Long ownerId = jwtService.getUserIdFromToken(token);
        Optional<TodoList> listOptional = todoListService.getListById(listId);
        if (!listOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("List not found.");
        }
        TodoList list = listOptional.get();
        if (!list.getOwnerId().equals(ownerId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only the list owner can add collaborators.");
        }
        todoListService.addCollaborator(listId, collaboratorId.getCollaboratorId());
        return ResponseEntity.ok().build();
    }


//    DELETING COLLABORATORS

    @DeleteMapping("/{listId}/collaborators/{collaboratorId}")
    public ResponseEntity<?> removeCollaborator(@PathVariable Long listId, @PathVariable Long collaboratorId, @RequestHeader("Authorization") String token) {
        Long ownerId = jwtService.getUserIdFromToken(token);
        Optional<TodoList> listOptional = todoListService.getListById(listId);
        if (!listOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("List not found.");
        }
        TodoList list = listOptional.get();
        if (!list.getOwnerId().equals(ownerId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only the list owner can remove collaborators.");
        }
        todoListService.removeCollaborator(listId, collaboratorId);
        return ResponseEntity.ok().build();
    }
//  GET THE LISTS IN THE SHARED WORKSPACE

    @GetMapping("/shared")
    public ResponseEntity<List<TodoList>> getSharedLists(@RequestHeader("Authorization") String token) {
        Long userId = jwtService.getUserIdFromToken(token);
        List<TodoList> collaboratedLists = todoListService.getListsAsCollaborator(userId);
        return ResponseEntity.ok(collaboratedLists);
    }


//    GET ALL THE LISTS, SHARED AND UNSHARED

    @GetMapping("/all")
    public ResponseEntity<List<TodoList>> getAllLists(@RequestHeader("Authorization") String token) {
        Long userId = jwtService.getUserIdFromToken(token);
        List<TodoList> ownedLists = todoListService.getListsByOwnerId(userId);
        List<TodoList> collaboratedLists = todoListService.getListsAsCollaborator(userId);
        ownedLists.addAll(collaboratedLists);
        return ResponseEntity.ok(ownedLists);
    }

}

