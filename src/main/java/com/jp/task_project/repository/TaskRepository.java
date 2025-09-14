package com.jp.task_project.repository;

import com.jp.task_project.entity.Image.Image;
import com.jp.task_project.entity.Task.Task;
import com.jp.task_project.entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository <Task, Long> {
    Optional<Task> findById(Long taskId);
    void deleteById(Long id);
    Task save(Task task);



    //Query personalizada para retornar todos as tesks do usuario x
    List<Task> findByUser_Id(Long userId);
}
