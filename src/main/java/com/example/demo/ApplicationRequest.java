package com.example.demo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ApplicationRequest")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ApplicationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "userName", length = 200)
    private String userName;

    @Column(name = "courseName", length = 200)
    String courseName;

    @Column(name = "phone", length = 200)
    String phone;

    @Column(name = "handled")
    boolean handled;

}
