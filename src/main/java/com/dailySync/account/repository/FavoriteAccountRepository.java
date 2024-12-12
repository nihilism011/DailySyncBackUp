package com.dailySync.account.repository;

import com.dailySync.account.entity.FavoriteAccount;
import com.dailySync.constant.AccountCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteAccountRepository extends JpaRepository<FavoriteAccount, Long> {
    List<FavoriteAccount> findByUserId(Long userId);

    List<FavoriteAccount> findByUserIdAndCategory(Long userId, AccountCategory category);


}
