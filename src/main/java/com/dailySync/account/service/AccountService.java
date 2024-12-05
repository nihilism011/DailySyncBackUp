package com.dailySync.account.service;

import com.dailySync.account.dto.AccountReqDto;
import com.dailySync.account.dto.AccountResDto;
import com.dailySync.account.dto.AccountSum;
import com.dailySync.account.dto.FavorAccountResDto;
import com.dailySync.account.entity.Account;
import com.dailySync.account.entity.FavoriteAccount;
import com.dailySync.account.repository.AccountRepository;
import com.dailySync.account.repository.FavoriteAccountRepository;
import com.dailySync.constant.ResMessage;
import com.dailySync.user.entities.User;
import com.dailySync.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final FavoriteAccountRepository favoriteRepository;

    public List<AccountResDto> selectAccountDate(Long userId, int year, int month, int day) {
        LocalDate date = LocalDate.of(year, month, day);
        List<Account> oneDayList = accountRepository.findByUserIdAndAccountDate(userId, date);
        return oneDayList.stream().map(Account::toResDto).toList();
    }

    public List<AccountSum> selectAccountMonth(Long userId, int year, int month) {
        return accountRepository.findByUserIdAndYearAndMonth(year, month, userId);
    }

    public boolean insertAccountItem(Long userId, AccountReqDto reqDto) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception(ResMessage.NOT_FOUND_USER));
        if (reqDto.getFixed()) {
            FavoriteAccount account = FavoriteAccount.of(user, reqDto);
            favoriteRepository.save(account);
        }
        accountRepository.save(Account.of(user, reqDto));
        return true;
    }

    public boolean updateAccountItem(Long accountId, AccountReqDto reqDto) throws Exception {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new Exception(ResMessage.NOT_FOUND));
        account.update(reqDto);
        accountRepository.save(account);
        return true;
    }

    public List<AccountResDto> selectFixedItems(Long userId, int year, int month) {
        List<Account> list = accountRepository.findByUserIdAndFixedAndYearAndMonth(userId, true, year, month);
        return list.stream().map(Account::toResDto).toList();
    }

    /**
     * favorite_account 테이블 CRUD
     */
    public boolean insertFavorAccountItem(Long userId, AccountReqDto reqDto) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception(ResMessage.NOT_FOUND_USER));
        FavoriteAccount account = FavoriteAccount.of(user, reqDto);
        favoriteRepository.save(account);
        return true;
    }

    public List<FavorAccountResDto> selectFavorAccountItems(Long userId, String category) {
        List<FavoriteAccount> accounts;
        if (category.equals("all") || category.equals("All") || category.equals("ALL")) {
            accounts = favoriteRepository.findByUserId(userId);
        } else {
            accounts = favoriteRepository.findByUserIdAndCategory(userId, category);
        }
        return accounts.stream().map(FavoriteAccount::toResDto).toList();

    }

    public boolean updateFavorAccountItem(Long favorAccountId, AccountReqDto reqDto) throws Exception {
        FavoriteAccount favorAccount = favoriteRepository.findById(favorAccountId).orElseThrow(() -> new Exception(ResMessage.NOT_FOUND));
        favorAccount.update(reqDto);
        favoriteRepository.save(favorAccount);
        return true;
    }

    public boolean deleteFavorAccountItem(Long favorAccountId) {
        favoriteRepository.deleteById(favorAccountId);
        return true;
    }
    //todo favorite_account 테이블에 대한 selectAll,update,delete 기능 구현해야함.

}
