package com.example.studentcounselingsystem.counseling.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

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
    @GeneratedValue(strategy=GenerationType.AUTO)
    private UUID id;
    @Column
    private UUID studentId;
    @Column
    private String content;
    @Column
    private UUID counselorId;
    @Column
    private String feedback;
    @Column
    private boolean isRead = false;
    @Column
    private LocalDateTime createdDate;

    public boolean getIsRead() {
        return this.isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }
}

