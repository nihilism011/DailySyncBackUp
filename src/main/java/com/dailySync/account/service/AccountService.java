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
import com.dailySync.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final UserService userService;
    private final AccountRepository accountRepository;
    private final FavoriteAccountRepository favoriteRepository;

    public void moveFixedAccountItem(User user) {
        LocalDate now = LocalDate.now();
        LocalDate lastLoginDate = user.getLastLogin().toLocalDate();
        int lastLoginYear = lastLoginDate.getYear();
        int nowYear = now.getYear();
        Month lastLoginMonth = lastLoginDate.getMonth();
        Month nowMonth = now.getMonth();
        if (lastLoginYear != nowYear || lastLoginMonth != nowMonth) {
            LocalDate startOfMonth = LocalDate.of(lastLoginYear, lastLoginMonth, 1);
            LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());
            List<Account> list = accountRepository.findByUserIdAndAccountDateBetweenAndFixedTrue(
                    user.getId(),
                    startOfMonth,
                    endOfMonth);
            for (Account a : list) {
                int fixedDate = a.getAccountDate().getDayOfMonth();
                int dayOfMonth = Math.min(fixedDate, now.lengthOfMonth());
                LocalDate date = LocalDate.of(nowYear, nowMonth, dayOfMonth);
                Account account = Account.builder()
                        .fixed(true)
                        .title(a.getTitle())
                        .user(user)
                        .accountDate(date)
                        .category(a.getCategory())
                        .description(a.getDescription())
                        .amount(a.getAmount())
                        .build();
                accountRepository.save(account);
            }
        }
    }

    //조회
    public List<AccountResDto> findAccountsByDate(Long userId, LocalDate date) {
        List<Account> oneDayList = accountRepository.findByUserIdAndAccountDate(userId, date);
        return oneDayList.stream().map(Account::toResDto).toList();
    }

    public List<AccountResDto> findAccountByMonth(Long userId,int year,int month){
        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());
        return accountRepository.findByUserIdAndAccountDateBetween(userId,startOfMonth,endOfMonth).stream().map(Account::toResDto).toList();
    }

    public AccountSum findSumByMonth(Long userId, int year, int month) {
        List<AccountSum> sumList = accountRepository.findByUserIdAndYearAndMonth(year, month, userId);
        Long PlusSum = 0L;
        Long minusSum = 0L;
        for (AccountSum item : sumList) {
            PlusSum += item.getPlusSumAmount();
            minusSum -= item.getMinusSumAmount();
        }
        AccountSum sum = new AccountSum(LocalDate.of(year,month,1));
        sum.setPlusSumAmount(PlusSum);
        sum.setMinusSumAmount(minusSum);
        return sum;
    }

    public List<AccountSum> findAccountsByMonth(Long userId, int year, int month) {
        return accountRepository.findByUserIdAndYearAndMonth(year, month, userId);
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
   public AccountSum findSumFixedByMonth(Long userId,int year,int month){
       LocalDate startOfMonth = LocalDate.of(year, month, 1);
       LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());
       List<Account> list = accountRepository.findByUserIdAndAccountDateBetweenAndFixedTrue(
               userId,
               startOfMonth,
               endOfMonth);
       long PlusSum = 0L;
       long minusSum = 0L;
       for (Account item : list) {
           if(item.getAmount()>0){
               PlusSum += item.getAmount();
           }else{
               minusSum -= item.getAmount();
           }
       }
       AccountSum sum = new AccountSum(LocalDate.of(year,month,1));
       sum.setPlusSumAmount(PlusSum);
       sum.setMinusSumAmount(minusSum);
       return sum;
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

        User user = userService.getUser(userId);
        accountRepository.save(Account.of(user, reqDto));
        return true;
    }

    public boolean createFavorAccountItem(Long userId, AccountReqDto reqDto) throws Exception {
        User user = userService.getUser(userId);
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
