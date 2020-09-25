package com.itaha.bugtracker.repository;

import com.itaha.bugtracker.entity.BugHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BugHistoryRepository extends JpaRepository<BugHistory, Long> {
}
