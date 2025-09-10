package com.jp.task_project.entity.Task;

import com.jp.task_project.entity.User.User;
import com.jp.task_project.utils.enums.TaskStatus;
import com.jp.task_project.utils.enums.TaskType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tasks")
@EntityListeners(AuditingEntityListener.class)
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_task")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TaskStatus status; // Usando o Enum para status


    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TaskType type;     // Usando o Enum para tipo

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "end_at")
    private LocalDateTime endAt;

    @ManyToOne
    @JoinColumn(name = "id_user") // Relacionamento com a entidade User
    private User user;

    public Task(String title, String description, TaskStatus status,
                TaskType type, User user) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.type = type;
        this.user = user;
    }

    public Task(String title, String description, TaskStatus status,
                TaskType type) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.type = type;

    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", type=" + type +
                ", createdAt=" + createdAt +
                ", endAt=" + endAt +
                ", user=" + user +
                '}';
    }
}