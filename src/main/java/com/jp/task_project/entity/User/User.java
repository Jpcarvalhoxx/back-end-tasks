package com.jp.task_project.entity.User;


import com.jp.task_project.entity.Task.Task;
import jakarta.persistence.*;import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "users")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id;

    @Column(name = "name_user")
    private String name;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name ="pass", nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;


    public User(String name, String email, String password){
        this.name = name;
        this.email =email;
        this.password = password;
    }
}
