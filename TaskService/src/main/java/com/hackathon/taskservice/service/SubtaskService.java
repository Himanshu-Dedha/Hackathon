package com.hackathon.taskservice.service;
import com.hackathon.taskservice.entity.Subtask;
import com.hackathon.taskservice.repository.SubtaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class SubtaskService {

    private final SubtaskRepository subtaskRepository;

    @Autowired
    public SubtaskService(SubtaskRepository subtaskRepository) {
        this.subtaskRepository = subtaskRepository;
    }

    public Subtask createSubtask(Subtask subtask) {
        return subtaskRepository.save(subtask);
    }

    public Optional<Subtask> updateSubtask(Long subtaskId, Subtask updatedSubtask) {
        return subtaskRepository.findById(subtaskId).map(subtask -> {
            subtask.setTitle(updatedSubtask.getTitle());
            subtask.setDescription(updatedSubtask.getDescription());
            subtask.setDeadline(updatedSubtask.getDeadline());
            subtask.setStatus(updatedSubtask.getStatus());
            subtask.setPriority(updatedSubtask.getPriority());
            return subtaskRepository.save(subtask);
        });
    }

    public Optional<Subtask> getSubtaskById(Long subtaskId) {
        return subtaskRepository.findById(subtaskId);
    }

    public List<Subtask> getSubtasksByParentTaskId(Long parentTaskId) {
        return subtaskRepository.findByParentTaskId(parentTaskId);
    }

    public void deleteSubtask(Long subtaskId) {
        subtaskRepository.deleteById(subtaskId);
    }
}