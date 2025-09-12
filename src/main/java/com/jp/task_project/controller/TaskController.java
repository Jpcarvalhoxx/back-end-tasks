package com.jp.task_project.controller;

import com.jp.task_project.dto.Task.TaskRequestCreateDTO;
import com.jp.task_project.dto.Task.TaskRequestUpdateDTO;
import com.jp.task_project.dto.Task.TaskResponseDTO;
import com.jp.task_project.service.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private TaskService taskService;



    @GetMapping("/{id}/all")
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks (@PathVariable("id") Long userId){
        List<TaskResponseDTO> allTasks =  taskService.getAllTaksByUser(userId);
        if(allTasks.isEmpty()) {
            return ResponseEntity.noContent().build(); // HTTP 204
        }
        return ResponseEntity.ok(allTasks);
    }

    @GetMapping("/{id}/info")
    public  ResponseEntity<TaskResponseDTO> getTask(@PathVariable("id") Long taskId){

        TaskResponseDTO t = taskService.getTaskById(taskId);
        return ResponseEntity.ok(t);

    }

    @PostMapping("/{id}/create")
    public  ResponseEntity<TaskResponseDTO> getTask(@RequestBody TaskRequestCreateDTO taskRequestCreateDTO){

        System.out.println(taskRequestCreateDTO.userId());
        TaskResponseDTO t = taskService.registerTaskInBD(taskRequestCreateDTO);
        return ResponseEntity.ok(t);

    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") Long id){
        taskService.deleteTaskById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping ("/{id}/update")
    public ResponseEntity<TaskResponseDTO> updatePatchTask(@PathVariable("id") Long taskId,@RequestBody  TaskRequestUpdateDTO
            taskRequestUpdateDTO){
        TaskResponseDTO t = taskService.updatePatchTaskInBd(taskRequestUpdateDTO,taskId);
        return ResponseEntity.ok(t);

    }

    @PutMapping ("/{id}/update")
    public ResponseEntity<TaskResponseDTO> updatePutTask(@PathVariable("id") Long taskId,@RequestBody  TaskRequestUpdateDTO
                                                              taskRequestUpdateDTO){
        TaskResponseDTO t = taskService.updatePutTaskInBd(taskRequestUpdateDTO,taskId);
        return ResponseEntity.ok(t);

    }



}
