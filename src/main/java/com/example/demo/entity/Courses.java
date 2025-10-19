package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Courses")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Courses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 200)
    private String name;

    @Column(name = "description", length = 200)
    private String description;

    @Column(name = "price")
    private Double price;

}
