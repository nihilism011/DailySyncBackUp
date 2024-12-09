package com.dailySync.account.repository;

import com.dailySync.account.dto.AccountSum;
import com.dailySync.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query ("SELECT new com.dailySync.account.dto.AccountSum(" +
            "a.accountDate, " +
            "SUM(CASE WHEN a.amount > 0 THEN a.amount ELSE 0 END), " +  // plusSumAmount
            "SUM(CASE WHEN a.amount < 0 THEN ABS(a.amount) ELSE 0 END)) " +  // minusSumAmount (절대값 사용)
            "FROM Account a " +
            "WHERE FUNCTION('YEAR', a.accountDate) = :year " +
            "AND FUNCTION('MONTH', a.accountDate) = :month " +
            "AND a.user.id = :userId " +
            "GROUP BY a.accountDate")
    List<AccountSum> findByUserIdAndYearAndMonth(@Param ("year") int year,
                                                 @Param ("month") int month,
                                                 @Param ("userId") Long userId);

    @Query ("SELECT new com.dailySync.account.dto.AccountSum(" +
            "a.accountDate, " +
            "SUM(CASE WHEN a.amount > 0 THEN a.amount ELSE 0 END), " +  // plusSumAmount
            "SUM(CASE WHEN a.amount < 0 THEN ABS(a.amount) ELSE 0 END)) " +  // minusSumAmount (절대값 사용)
            "FROM Account a " +
            "WHERE a.accountDate = :date " +
            "AND a.user.id = :userId " +
            "GROUP BY a.accountDate")
    Optional<AccountSum> findSumByUserIdAndDate(
            @Param ("date") LocalDate date,
            @Param ("userId") Long userId);

    List<Account> findByUserIdAndAccountDateBetweenAndFixedTrue(
            Long userId,
            LocalDate startDate,
            LocalDate endDate);

    List<Account> findByUserIdAndAccountDate(Long userId, LocalDate accountDate);
}
