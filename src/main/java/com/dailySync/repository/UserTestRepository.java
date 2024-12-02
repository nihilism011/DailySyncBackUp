package com.dailySync.repository;

import com.dailySync.entities.UserTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTestRepository extends JpaRepository<UserTest,Long> {
    public Optional<UserTest> findByUserName(String userName);

    public Optional<UserTest> findById(Long id);

}