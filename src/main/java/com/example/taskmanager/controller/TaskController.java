package com.example.taskmanager.controller;

import com.example.taskmanager.entity.Task;
import com.example.taskmanager.entity.User;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.repository.UserRepository;
import com.example.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskRepository taskRepo;
    private final UserRepository userRepo;

    private TaskService taskService;


    public TaskController(TaskRepository taskRepo, UserRepository userRepo, TaskService taskService) {
        this.taskRepo = taskRepo;
        this.userRepo = userRepo;
        this.taskService = taskService;
    }


    private String currentUser(){
        return SecurityContextHolder.getContext()
                .getAuthentication().getName();
    }

    @GetMapping
    public List<Task> getTasks()
    {

        return taskRepo.findByUserUsername(currentUser());
    }

    @PostMapping
    public Task createTask(@RequestBody Task task)
    {

        User user=userRepo.findByUsername(currentUser()).get();
        task.setUser(user);
        return taskRepo.save(task);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id,
                           @RequestBody Task task)
    {
        return taskService.updateTask(id,task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id)
    {

        taskRepo.deleteById(id);
    }
}
