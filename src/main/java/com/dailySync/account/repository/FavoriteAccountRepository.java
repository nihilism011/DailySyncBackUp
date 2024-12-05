package com.dailySync.account.repository;

import com.dailySync.account.entity.FavoriteAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteAccountRepository extends JpaRepository<FavoriteAccount, Long> {
    List<FavoriteAccount> findByUserId(Long userId);

    List<FavoriteAccount> findByUserIdAndCategory(Long userId, String category);
}
