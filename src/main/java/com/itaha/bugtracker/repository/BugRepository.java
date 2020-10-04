package com.itaha.bugtracker.repository;

import com.itaha.bugtracker.entity.Bug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BugRepository extends JpaRepository<Bug, Long> {
    List<Bug> findAllByPriorityEquals(String priority);
    List<Bug> findAllByTypeEquals(String type);
    List<Bug> findAllByStatusEquals(String status);
}
