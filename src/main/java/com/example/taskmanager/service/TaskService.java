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

    public Task updateTask(Long id, Task updatedTask){
        Task existingTask = taskRespository.findById(id).orElseThrow(
                () -> new RuntimeException("task not found"));

        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setCompleted(updatedTask.isCompleted());

        return taskRespository.save(existingTask);


    }

    public void deleteTask(Long id){
         taskRespository.deleteById(id);
    }

}
