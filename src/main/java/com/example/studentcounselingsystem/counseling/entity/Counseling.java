package com.example.studentcounselingsystem.counseling.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "counseling")
public class Counseling {
    @Id
    @Column
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @Column
    private int studentId;
    @Column
    private String content;
    @Column
    private int counselorId;
    @Column
    private String feedback;
    @Column
    private boolean isRead;
    @Column
    private LocalDateTime createdDate;

    public boolean getIsRead() {
        return this.isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }
}

