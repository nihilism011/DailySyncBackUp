package com.dailySync.account.repository;

import com.dailySync.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    List<Account> findByUser_IdAndAccountDate(Long userId, LocalDate accountDate);
}
