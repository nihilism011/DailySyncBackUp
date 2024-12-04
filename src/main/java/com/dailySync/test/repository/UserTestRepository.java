package com.dailySync.test.repository;

import com.dailySync.test.entities.UserTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTestRepository extends JpaRepository<UserTest,Long> {
    Optional<UserTest> findByUserName(String userName);

    Optional<UserTest> findById(Long id);

}