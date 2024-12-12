package com.dailySync.account.service;

import com.dailySync.account.dto.AccountReqDto;
import com.dailySync.account.dto.AccountResDto;
import com.dailySync.account.dto.AccountSum;
import com.dailySync.account.dto.FavorAccountResDto;
import com.dailySync.account.entity.Account;
import com.dailySync.account.entity.FavoriteAccount;
import com.dailySync.account.repository.AccountRepository;
import com.dailySync.account.repository.FavoriteAccountRepository;
import com.dailySync.constant.AccountCategory;
import com.dailySync.constant.ResMessage;
import com.dailySync.user.entities.User;
import com.dailySync.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final FavoriteAccountRepository favoriteRepository;

    private User getUserById(Long userId) throws Exception {
        return userRepository.findById(userId)
                .orElseThrow(() -> new Exception(ResMessage.NOT_FOUND_USER));
    }

    //조회
    public List<AccountResDto> findAccountsByDate(Long userId, LocalDate date) {
        List<Account> oneDayList = accountRepository.findByUserIdAndAccountDate(userId, date);
        return oneDayList.stream().map(Account::toResDto).toList();
    }

    public List<AccountSum> findAccountsByMonth(Long userId, int year, int month) {
        int prevMonth = month == 1 ? 12 : month - 1;
        int prevYear = (month == 1) ? year - 1 : year;

        // 다음 달 계산
        int nextMonth = month == 12 ? 1 : month + 1;
        int nextYear = (month == 12) ? year + 1 : year;

        // 결과 리스트 초기화
        List<AccountSum> result = new ArrayList<>();
        List<AccountSum> prev = accountRepository.findByUserIdAndYearAndMonth(prevYear, prevMonth, userId);
        List<AccountSum> current = accountRepository.findByUserIdAndYearAndMonth(year, month, userId);
        List<AccountSum> next = accountRepository.findByUserIdAndYearAndMonth(nextYear, nextMonth, userId);
        result.addAll(prev);
        result.addAll(current);
        result.addAll(next);
        return result;
    }

    public List<AccountResDto> findFixedAccounts(Long userId, int year, int month) {
        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());
        List<Account> list = accountRepository.findByUserIdAndAccountDateBetweenAndFixedTrue(
                userId,
                startOfMonth,
                endOfMonth);
        return list.stream().map(Account::toResDto).toList();
    }

    public List<FavorAccountResDto> findFavorAccountItems(Long userId, String category) {
        List<FavoriteAccount> accounts;
        if (category.equals("all") || category.equals("All") || category.equals("ALL")) {
            accounts = favoriteRepository.findByUserId(userId);
        } else {
            accounts = favoriteRepository.findByUserIdAndCategory(userId, AccountCategory.fromString(category));
        }
        return accounts.stream().map(FavoriteAccount::toResDto).toList();
    }

    public AccountSum findSumAccountByDate(Long userId, LocalDate date) {

        return accountRepository.findSumByUserIdAndDate(date, userId).orElse(new AccountSum(date));
    }

    //생성
    public boolean createAccountItem(Long userId, AccountReqDto reqDto) throws Exception {
        User user = getUserById((userId));
        accountRepository.save(Account.of(user, reqDto));
        return true;
    }

    public boolean createFavorAccountItem(Long userId, AccountReqDto reqDto) throws Exception {
        User user = getUserById((userId));
        FavoriteAccount account = FavoriteAccount.of(user, reqDto);
        favoriteRepository.save(account);
        return true;
    }

    //수정
    public boolean updateAccountItem(Long accountId, AccountReqDto reqDto) throws Exception {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new Exception(ResMessage.NOT_FOUND));
        account.update(reqDto);
        accountRepository.save(account);
        return true;
    }

    public boolean updateFavorAccountItem(Long favorAccountId, AccountReqDto reqDto) throws Exception {
        FavoriteAccount favorAccount = favoriteRepository.findById(favorAccountId).orElseThrow(() -> new Exception(ResMessage.NOT_FOUND));
        favorAccount.update(reqDto);
        favoriteRepository.save(favorAccount);
        return true;
    }

    //삭제
    public boolean deleteAccountItem(Long accountId) {
        accountRepository.deleteById(accountId);
        return true;
    }

    public boolean deleteFavorAccountItem(Long favorAccountId) {
        favoriteRepository.deleteById(favorAccountId);
        return true;
    }
}
