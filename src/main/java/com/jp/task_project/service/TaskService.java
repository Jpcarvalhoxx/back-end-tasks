package com.jp.task_project.service;

import com.jp.task_project.dto.Task.RegisterImageTaskDTO;
import com.jp.task_project.dto.Task.TaskRequestCreateDTO;
import com.jp.task_project.dto.Task.TaskRequestUpdateDTO;
import com.jp.task_project.dto.Task.TaskResponseDTO;
import com.jp.task_project.entity.Image.Image;
import com.jp.task_project.entity.Task.Task;
import com.jp.task_project.entity.User.User;
import com.jp.task_project.exeception.CloudinaryUploadService;
import com.jp.task_project.exeception.ImageNotFoundException;
import com.jp.task_project.exeception.TaskNotFoundException;
import com.jp.task_project.exeception.UserNotFoundException;
import com.jp.task_project.mapper.TaskMapper;
import com.jp.task_project.repository.ImageRepository;
import com.jp.task_project.repository.TaskRepository;
import com.jp.task_project.repository.UserRepository;

import com.jp.task_project.utils.cloudinary.CloudinaryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;


    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ImageRepository imageRepository;




    private final TaskMapper taskMapper;

    public TaskService (TaskMapper taskMapper){
        this.taskMapper = taskMapper;
    }

    @Transactional
    public TaskResponseDTO registerTaskInBD(TaskRequestCreateDTO taskRequest, Long userId){

        User u = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("Usuário não encontrado com id: " + userId));


            Task t = taskMapper.toCreateTask(taskRequest);
            t.setUser(u);
               List<Image>im  = saveImages(taskRequest.imgs(),t);
              t.setImgs(im);

                t = taskRepository.save(t);

        return  taskMapper.toDto(t);

    }

    @Transactional
    public TaskResponseDTO updatePutTaskInBd(TaskRequestUpdateDTO taskRequestUpdateDTO, Long taskId){

        Task t = taskRepository.findById(taskId).orElseThrow(() ->
                new TaskNotFoundException("Task not found: "+taskId ));

        taskMapper.toUpdateTask(taskRequestUpdateDTO,t);
        taskRepository.save(t);

        return taskMapper.toDto(t);

    }

    @Transactional
    public TaskResponseDTO updatePatchTaskInBd(TaskRequestUpdateDTO taskRequestUpdateDTO, Long taskId){

        Task t = taskRepository.findById(taskId).orElseThrow(() ->
                new TaskNotFoundException("Task not found: "+taskId ));

        taskMapper.partialUpdate(taskRequestUpdateDTO,t);
        taskRepository.save(t);

        return taskMapper.toDto(t);

    }



    public List<TaskResponseDTO> getAllTaksByUser(Long userId) {
        // Verifica se o usuário existe
        userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("Usuário não encontrado com id: " + userId));

        // Busca as tasks do usuário
        List<Task> tasks = taskRepository.findByUser_Id(userId);

        // Converte cada Task para TaskResponseDTO
        return taskMapper.toDtoList(tasks);
    }

    public TaskResponseDTO getTaskById(Long taskId){
        Task task  = taskRepository.findById(taskId)
                .orElseThrow(()->new TaskNotFoundException("Task not found"));
        return taskMapper.toDto(task);

    }
    @Transactional
    public void deleteTaskById(Long id){

        taskRepository.findById(id).orElseThrow(()-> new TaskNotFoundException("Task not found"));
        taskRepository.deleteById(id);

    }

    @Transactional
    public void deleteByUrl(List<String> urls){


      if (!imageRepository.findByImageUrlIn(urls).isEmpty()){
          imageRepository.deleteByImageUrlIn(urls);
      }else {
          throw  new ImageNotFoundException("Images not found");
      }

    }



    @Transactional
    public List<Image> registerImgsTask(RegisterImageTaskDTO regImgs, Long idTask){

        Task t = taskRepository.findById(idTask).orElseThrow(()-> new TaskNotFoundException("Task not found"));
        List<Image>im  = saveImages(regImgs.imgs(),t);
        return imageRepository.saveAll(im);

        }


        private List<Image> saveImages( List<MultipartFile> images, Task t){

            List<Image>im = new ArrayList<>();
            for (MultipartFile img: images){

                Image i = new Image();
                try {
                    String urlCloudinary = cloudinaryService.uploadImage(img);
                    i.setImageUrl(urlCloudinary);
                } catch (Exception e) {
                    throw new CloudinaryUploadService("Falha ao enviar imagem: " + img.getOriginalFilename());
                }
                i.setTask(t);
                im.add(i);

            }


        return im;}

    }



