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

public class BugHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String property;
    private String oldValue;
    private String newValue;
    private LocalDateTime date;
    @ManyToOne(fetch = FetchType.LAZY)
    private Bug bug;
}
