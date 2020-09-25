package com.itaha.bugtracker.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String title;
    private String description;

    @ManyToOne
    private User createdBy;
    private LocalDateTime dateCreation;

    @ManyToOne
    private User managedBy;
    private LocalDateTime dateAssigned;

    @ManyToOne
    private User updatedBy;
    private LocalDateTime dateUpdated;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> personnelAssigned;

    @OneToMany
    private List<Bug> bugs;


}
