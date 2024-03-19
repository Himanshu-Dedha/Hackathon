package com.hackathon.listservice.repository;

import com.hackathon.listservice.entity.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Long> {

    List<TodoList> findByOwnerId(Long ownerId);

    @Query("SELECT l FROM TodoList l WHERE :userId MEMBER OF l.collaborators")
    List<TodoList> findListsWhereUserIsCollaborator(@Param("userId") Long userId);

}
