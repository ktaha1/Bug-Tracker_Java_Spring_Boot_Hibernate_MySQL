package com.itaha.bugtracker.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bugs")
public class Bug {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String title;
    private String description;
    private String status;
    private String priority;
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    private User developerAssigned;

    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;
    private LocalDateTime dateCreation;

    @ManyToOne(fetch = FetchType.LAZY)
    private User updatedBy;
    private LocalDateTime dateUpdate;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Comment> comments;
    @OneToMany(fetch = FetchType.LAZY)
    private List<BugHistory> bugHistories;


}
