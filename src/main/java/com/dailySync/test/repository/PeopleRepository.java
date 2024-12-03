package com.dailySync.test.repository;

import com.dailySync.test.entities.People;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface PeopleRepository extends JpaRepository<People, Long> {
    Optional<People> findById(Long id);
}
