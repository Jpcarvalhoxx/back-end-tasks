package com.jp.task_project.service;

import com.jp.task_project.dto.Task.TaskRequestCreateDTO;
import com.jp.task_project.dto.Task.TaskRequestUpdateDTO;
import com.jp.task_project.dto.Task.TaskResponseDTO;
import com.jp.task_project.entity.Task.Task;
import com.jp.task_project.entity.User.User;
import com.jp.task_project.exeception.TaskNotFoundException;
import com.jp.task_project.exeception.UserNotFoundException;
import com.jp.task_project.repository.TaskRepository;
import com.jp.task_project.repository.UserRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TaskService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Transactional
    public TaskResponseDTO registerTaskInBD(TaskRequestCreateDTO taskRequest){

        User u = userRepository.findById(taskRequest.userId()).orElseThrow(() ->
                new UserNotFoundException("Usuário não encontrado com id: " + taskRequest.userId()));
        Task t = TaskRequestCreateDTO.convertToTask(taskRequest,u);

        t = taskRepository.save(t);

        return TaskResponseDTO.from(t);

    }

    @Transactional
    public TaskResponseDTO updatePutTaskInBd(TaskRequestUpdateDTO taskRequestUpdateDTO, Long taskId){

        Task t = taskRepository.findById(taskId).orElseThrow(() ->
                new TaskNotFoundException("Task not found: "+taskId ));

        t.setTitle(taskRequestUpdateDTO.title());
        t.setDescription(taskRequestUpdateDTO.description());
        t.setType(taskRequestUpdateDTO.type());
        t.setStatus(taskRequestUpdateDTO.status());

        taskRepository.save(t);

        return TaskResponseDTO.from(t);

    }

    @Transactional
    public TaskResponseDTO updatePatchTaskInBd(TaskRequestUpdateDTO taskRequestUpdateDTO, Long taskId){

        Task t = taskRepository.findById(taskId).orElseThrow(() ->
                new TaskNotFoundException("Task not found: "+taskId ));

        if (taskRequestUpdateDTO.title()!=null){
            t.setTitle(taskRequestUpdateDTO.title());
        }

        if (taskRequestUpdateDTO.description()!=null){
            t.setDescription(taskRequestUpdateDTO.description());
        }

        if (taskRequestUpdateDTO.status() !=null){
            t.setStatus(taskRequestUpdateDTO.status());
        }

        if (taskRequestUpdateDTO.type()!=null){
            t.setType(taskRequestUpdateDTO.type());
        }

        taskRepository.save(t);

        return TaskResponseDTO.from(t);

    }



    public List<TaskResponseDTO> getAllTaksByUser(Long userId) {
        // Verifica se o usuário existe
        userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("Usuário não encontrado com id: " + userId));

        // Busca as tasks do usuário
        List<Task> tasks = taskRepository.findByUser_Id(userId);

        // Converte cada Task para TaskResponseDTO
        return tasks.stream()
                .map(TaskResponseDTO::from)
                .toList();
    }

    public TaskResponseDTO getTaskById(Long taskId){
        Task task  = taskRepository.findById(taskId)
                .orElseThrow(()->new TaskNotFoundException("Task not found"));
        return TaskResponseDTO.from(task);

    }
    @Transactional
    public void deleteTaskById(Long id){

        taskRepository.findById(id).orElseThrow(()-> new TaskNotFoundException("Task not found"));
        taskRepository.deleteById(id);

    }


}
