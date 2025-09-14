package com.jp.task_project.repository;

import com.jp.task_project.entity.Image.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository <Image, Long> {
    List<Image> findByImageUrlIn(List<String> urls);
    void deleteByImageUrlIn(List<String> imageUrl);

}
