package com.example.studentcounselingsystem.student.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student")
public class Student {
    @Id
    @Column
    @GeneratedValue(strategy=GenerationType.AUTO)
    private UUID id;
    @Column
    private String name;
    @Column
    private String status;
}
