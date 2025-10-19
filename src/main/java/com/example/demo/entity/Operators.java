package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Operators")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Operators {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 200)
    private String name;

    @Column(name = "surname", length = 200)
    private String surname;

    @Column(name = "department")
    private String department;

}
