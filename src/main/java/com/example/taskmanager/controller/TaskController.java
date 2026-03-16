package com.example.taskmanager.controller;

import com.example.taskmanager.entity.Task;
import com.example.taskmanager.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskRepository repo;
    private final AuthController auth;
    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    public TaskController(TaskRepository repo, AuthController auth) {
        this.repo = repo;
        this.auth = auth;
    }

    private String validate(String token){
        if(!auth.isValidToken(token))
            throw new RuntimeException("Unauthorized");
        return auth.getUsername(token);
    }

    @GetMapping
    public List<Task> all(@RequestHeader("Authorization") String token){
        log.info("the authorization recieved is :  "+ token);
        return repo.findByUsername(validate(token));
    }

    @PostMapping
    public Task create(@RequestHeader("Authorization") String token,
                       @RequestBody Task task){

        log.info("the authorization recieved is in create :  "+ token);
        task.setUsername(validate(token));
        return repo.save(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@RequestHeader("Authorization") String token,
                                    @PathVariable Long id){

        validate(token);
        repo.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }
}