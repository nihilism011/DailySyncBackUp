package com.dailySync.account.controller;

import com.dailySync.account.dto.AccountListResDto;
import com.dailySync.account.dto.AccountReqDto;
import com.dailySync.account.service.AccountService;
import com.dailySync.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    /**로그인 한 유저가 날짜를 date 변수에 담아 넘겨주면 해당 날짜의 accountList 를 전달 */
    @GetMapping ("account/dayList/{date}")
    public ResponseEntity<ApiResponse<AccountListResDto>> oneDayAccount(@PathVariable LocalDate date) {
        return ApiResponse.success(accountService.getOneDayAccount(date));
    }
    @PostMapping("account/item")
    public ResponseEntity<ApiResponse<Boolean>> addAccountItem(@RequestBody AccountReqDto reqDto) throws Exception {
        //todo 유저 아이디 (하드코딩) 시큐리티 세션에서 꺼내 사용하는 방식으로 변경해야함.
        Long userId = 3L;
        return ApiResponse.success(accountService.addAccountItem(userId,reqDto));
    }

}
