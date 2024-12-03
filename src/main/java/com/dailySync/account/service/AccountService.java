package com.dailySync.account.service;

import com.dailySync.account.dto.AccountListResDto;
import com.dailySync.account.dto.AccountReqDto;
import com.dailySync.account.dto.AccountResDto;
import com.dailySync.account.entity.Account;
import com.dailySync.account.repository.AccountRepository;
import com.dailySync.account.repository.FavoriteAccountRepository;
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

    public AccountListResDto accountPageInfo(Long userId,int year,int month, int day) {
        LocalDate date = LocalDate.of(year,month,day);
        List<Account> oneMonthList= accountRepository.findByUserIdAndYearAndMonth(year,month,userId);
        List<Account> oneDayList = accountRepository.findByUser_IdAndAccountDate(userId, date);
        //todo 두 리스트 리스트 dto 로 담아서 컨트롤러로 보낼것.
        return new AccountListResDto(oneDayList.stream().map(Account::toResDto).toList());
    }

    public boolean addAccountItem(Long userId, AccountReqDto reqDto) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("user doesn't exist"));
        Account account = accountRepository.save(Account.of(user,reqDto));
        return true;
    }
}
