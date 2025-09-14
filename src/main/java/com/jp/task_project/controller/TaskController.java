package com.jp.task_project.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.jp.task_project.dto.Task.RegisterImageTaskDTO;
import com.jp.task_project.dto.Task.TaskRequestCreateDTO;
import com.jp.task_project.dto.Task.TaskRequestUpdateDTO;
import com.jp.task_project.dto.Task.TaskResponseDTO;
import com.jp.task_project.entity.Image.Image;
import com.jp.task_project.service.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.awt.print.Book;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private Cloudinary cloudinary;


    @GetMapping("/{id}/all")
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks(@PathVariable("id") Long userId) {
        List<TaskResponseDTO> allTasks = taskService.getAllTaksByUser(userId);
        if (allTasks.isEmpty()) {
            return ResponseEntity.noContent().build(); // HTTP 204
        }
        return ResponseEntity.ok(allTasks);
    }

    @GetMapping("/{id}/info")
    public ResponseEntity<TaskResponseDTO> getTask(@PathVariable("id") Long taskId) {

        TaskResponseDTO t = taskService.getTaskById(taskId);
        return ResponseEntity.ok(t);

    }

    @PostMapping("/{id}/create")
    public ResponseEntity<TaskResponseDTO> getTask(@PathVariable("id") Long id, @ModelAttribute  TaskRequestCreateDTO taskRequestCreateDTO) {
        TaskResponseDTO t = taskService.registerTaskInBD(taskRequestCreateDTO, id);
        return ResponseEntity.ok(t);

    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") Long id) {
        taskService.deleteTaskById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<TaskResponseDTO> updatePatchTask(@PathVariable("id") Long taskId, @ModelAttribute TaskRequestUpdateDTO
            taskRequestUpdateDTO) {


        TaskResponseDTO t = taskService.updatePatchTaskInBd(taskRequestUpdateDTO, taskId);
        return ResponseEntity.ok(t);

    }

    @PutMapping("/{id}/update")
    public ResponseEntity<TaskResponseDTO> updatePutTask(@PathVariable("id") Long id, @ModelAttribute TaskRequestUpdateDTO
            taskRequestUpdateDTO) {

        System.out.println(taskRequestUpdateDTO.imgs().get(0).getOriginalFilename());
        TaskResponseDTO t = taskService.updatePutTaskInBd(taskRequestUpdateDTO, id);
        return ResponseEntity.ok(t);

    }

    @PostMapping("/{id}/image-register")
    public ResponseEntity <List<Image>> registerImgTask(@PathVariable("id") Long id, @ModelAttribute RegisterImageTaskDTO
            registerImageTaskDTO) {
        List<Image> t = taskService.registerImgsTask(registerImageTaskDTO, id);
        return ResponseEntity.ok(t);
    }

  /*  @PostMapping("/upload")
    public ResponseEntity<List<Map<String, Object>>> addBook(
            @RequestParam String name,
            @RequestParam("imgUrl") List<MultipartFile> imgs) {

        List<Map<String, Object>> uploadResult = new ArrayList<>();
        File convFile = null;

        try {
            for (MultipartFile imgUrl : imgs) {
                // 🧹 Sanitize nome do arquivo
                String fileName = Paths.get(imgUrl.getOriginalFilename()).getFileName().toString();
                fileName = fileName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");

                // 📂 Cria arquivo temporário
                convFile = new File(System.getProperty("java.io.tmpdir") + "/" + fileName);
                try (FileOutputStream fos = new FileOutputStream(convFile)) {
                    fos.write(imgUrl.getBytes());
                }

                // ☁️ Upload no Cloudinary
                Map<String, Object> result = cloudinary.uploader().upload(convFile, ObjectUtils.asMap(
                        "folder", "bookCovers"
                ));
                uploadResult.add(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Erro ao enviar arquivo para o Cloudinary.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado.");
        } finally {
            // 🧼 Remove o arquivo temporário
            if (convFile != null && convFile.exists()) {
                convFile.delete();
            }
        }

        List<Map<String, Object>> simplifiedList = uploadResult.stream().map(map -> {
            Map<String, Object> m = new HashMap<>();
            m.put("fileName", map.get("original_filename"));
            m.put("url", map.get("secure_url"));
            return m;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(simplifiedList);

        return ResponseEntity.ok(uploadResult);
    }
*/

}