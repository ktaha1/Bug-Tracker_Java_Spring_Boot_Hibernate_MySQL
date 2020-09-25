package com.itaha.bugtracker.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String message;
    @ManyToOne(fetch = FetchType.LAZY)
    private Bug bug;
    @ManyToOne(fetch = FetchType.LAZY)
    private User commentedBy;
    private LocalDateTime dateCreation;

}
