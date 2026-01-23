package com.example.taskmanager.service;

import com.example.taskmanager.entity.Task;
import com.example.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TaskService {

    private TaskRepository taskRespository;

    public TaskService(TaskRepository taskRespository) {
        this.taskRespository = taskRespository;
    }

    public Task createTask(Task task){
        taskRespository.save(task);
        return task;
    }

    public List<Task> getAllTasks(){
        List<Task> tasks = taskRespository.findAll();
        return tasks;
    }

    public boolean updateTask(Long id, Task task){
        Optional<Task> foundTask = taskRespository.findById(id);
        if(foundTask.isPresent())
        {
            foundTask = Optional.ofNullable(task);
            return true;
        }
        return false;
    }

    public void deleteTask(Long id){
        taskRespository.deleteById(id);
    }

}
