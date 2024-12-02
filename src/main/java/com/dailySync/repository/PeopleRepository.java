package com.dailySync.repository;

import com.dailySync.entities.People;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface PeopleRepository extends JpaRepository<People, Long> {
    public Optional<People> findById(Long id);
}
