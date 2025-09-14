package com.jp.task_project.entity.Image;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.jp.task_project.entity.Task.Task;
import com.jp.task_project.entity.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_image")

    private Long id;

    @Column(name = "image_url")
    private String imageUrl;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_task")
    private Task task;
}
