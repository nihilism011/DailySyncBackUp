package com.dailySync.account.controller;

import com.dailySync.account.dto.AccountReqDto;
import com.dailySync.account.dto.AccountResDto;
import com.dailySync.account.dto.FavorAccountResDto;
import com.dailySync.account.service.AccountService;
import com.dailySync.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping ("api/account")
public class AccountController {
    private final AccountService accountService;

    /**
     * 로그인 한 유저가 날짜를 date 변수에 담아 넘겨주면 해당 날짜의 accountList 를 전달
     */
    @GetMapping ("date/{year}/{month}/{day}")
    public ResponseEntity<ApiResponse<List<AccountResDto>>> accountDate(
            @PathVariable ("year") int year,
            @PathVariable ("month") int month,
            @PathVariable ("day") int day
    ) {
        //todo 유저 아이디 (하드코딩) 시큐리티 세션에서 꺼내 사용하는 방식으로 변경해야함.
        Long userId = 3L;
        return ApiResponse.success(accountService.selectAccountDate(userId, year, month, day));
    }

    @GetMapping ("month/{year}/{month}")
    public ResponseEntity<?> accountMonth(
            @PathVariable ("year") int year,
            @PathVariable ("month") int month
    ) {
        //todo 유저 아이디 (하드코딩) 시큐리티 세션에서 꺼내 사용하는 방식으로 변경해야함.
        Long userId = 3L;
        return ApiResponse.success((accountService.selectAccountMonth(userId, year, month)));
    }

    @PostMapping ("item")
    public ResponseEntity<ApiResponse<Boolean>> addAccountItem(@RequestBody AccountReqDto reqDto) throws Exception {
        //todo 유저 아이디 (하드코딩) 시큐리티 세션에서 꺼내 사용하는 방식으로 변경해야함.
        Long userId = 3L;
        return ApiResponse.success(accountService.insertAccountItem(userId, reqDto));
    }

    @PutMapping ("item/{accountId}")
    public ResponseEntity<ApiResponse<Boolean>> putAccountItem(
            @PathVariable ("accountId") Long accountId,
            @RequestBody AccountReqDto reqDto) throws Exception {
        return ApiResponse.success(accountService.updateAccountItem(accountId, reqDto));
    }

    @PatchMapping ("item/{accountId}")
    public ResponseEntity<ApiResponse<Boolean>> patchAccountItem(
            @PathVariable ("accountId") Long accountId,
            @RequestBody AccountReqDto reqDto) throws Exception {
        return ApiResponse.success(accountService.updateAccountItem(accountId, reqDto));
    }

    @GetMapping ("fixedItem/{year}/{month}")
    public ResponseEntity<ApiResponse<List<AccountResDto>>> getFixedItem(@PathVariable ("year") int year,
                                                                         @PathVariable ("month") int month) {
        //todo 유저 아이디 (하드코딩) 시큐리티 세션에서 꺼내 사용하는 방식으로 변경해야함.
        Long userId = 3L;
        return ApiResponse.success(accountService.selectFixedItems(userId, year, month));
    }

    @GetMapping ("item/favor/{category}")
    public ResponseEntity<ApiResponse<List<FavorAccountResDto>>> getFavorItems(
            @PathVariable ("category") String category) {
        //todo 유저 아이디 (하드코딩) 시큐리티 세션에서 꺼내 사용하는 방식으로 변경해야함.
        Long userId = 3L;
        return ApiResponse.success(accountService.selectFavorAccountItems(userId, category));
    }

    @PostMapping ("item/favor")
    public ResponseEntity<ApiResponse<Boolean>> addFavorItem(AccountReqDto reqDto) throws Exception {
        //todo 유저 아이디 (하드코딩) 시큐리티 세션에서 꺼내 사용하는 방식으로 변경해야함.
        Long userId = 3L;
        return ApiResponse.success(accountService.insertFavorAccountItem(userId, reqDto));
    }

    @PutMapping ("item/favor/{favorAccountId}")
    public ResponseEntity<ApiResponse<Boolean>> putFavorAccount(
            @PathVariable Long favorAccountId,
            @RequestBody AccountReqDto reqDto) throws Exception {
        return ApiResponse.success(accountService.updateFavorAccountItem(favorAccountId, reqDto));
    }

    @PatchMapping ("item/favor/{favorAccountId}")
    public ResponseEntity<ApiResponse<Boolean>> patchFavorAccount(
            @PathVariable Long favorAccountId,
            @RequestBody AccountReqDto reqDto) throws Exception {
        return ApiResponse.success(accountService.updateFavorAccountItem(favorAccountId, reqDto));
    }

    @DeleteMapping ("item/favor/{favorAccountId}")
    public ResponseEntity<ApiResponse<Boolean>> removeFavorAccountItem(@PathVariable Long favorAccountId) {
        return ApiResponse.success(accountService.deleteFavorAccountItem(favorAccountId));
    }
}
