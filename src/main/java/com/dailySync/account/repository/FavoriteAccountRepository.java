package com.dailySync.account.repository;

import com.dailySync.account.entity.FavoriteAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteAccountRepository extends JpaRepository<FavoriteAccount,Long> {
}
