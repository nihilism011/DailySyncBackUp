package com.dailySync.account.repository;

import com.dailySync.account.dto.AccountSum;
import com.dailySync.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

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

    @Query ("SELECT a FROM Account a " +
            "WHERE FUNCTION('YEAR', a.accountDate) = :year " +
            "AND FUNCTION('MONTH', a.accountDate) = :month " +
            "AND a.user.id = :userId " +
            "AND a.fixed = :fixed " +
            "ORDER BY a.amount DESC")
    List<Account> findByUserIdAndFixedAndYearAndMonth(@Param ("userId") Long userId,
                                                      @Param ("fixed") boolean fixed,
                                                      @Param ("year") int year,
                                                      @Param ("month") int month);

    List<Account> findByUserIdAndAccountDate(Long userId, LocalDate accountDate);
}
