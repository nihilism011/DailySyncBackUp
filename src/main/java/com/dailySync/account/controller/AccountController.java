package com.dailySync.account.controller;

import com.dailySync.account.dto.AccountReqDto;
import com.dailySync.account.dto.AccountResDto;
import com.dailySync.account.dto.FavorAccountResDto;
import com.dailySync.account.service.AccountService;
import com.dailySync.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping ("api/account")
public class AccountController {
    private final AccountService accountService;

    @Tag (name = "ACCOUNT", description = "가계부")
    @Operation (summary = "해당 날짜의 가계부 목록")
    @GetMapping ("item/date/{year}/{month}/{day}")
    public ResponseEntity<ApiResponse<List<AccountResDto>>> accountDate(
            @PathVariable ("year") int year,
            @PathVariable ("month") int month,
            @PathVariable ("day") int day
    ) {
        //todo 유저 아이디 (하드코딩) 시큐리티 세션에서 꺼내 사용하는 방식으로 변경해야함.
        Long userId = 3L;
        return ApiResponse.success(accountService.selectAccountDate(userId, year, month, day));
    }

    @Tag (name = "ACCOUNT", description = "가계부")
    @Operation (summary = "해당 월에 각 날짜별 수입,지출의 합산")
    @GetMapping ("item/month/{year}/{month}")
    public ResponseEntity<?> accountMonth(
            @PathVariable ("year") int year,
            @PathVariable ("month") int month
    ) {
        //todo 유저 아이디 (하드코딩) 시큐리티 세션에서 꺼내 사용하는 방식으로 변경해야함.
        Long userId = 3L;
        return ApiResponse.success((accountService.selectAccountMonth(userId, year, month)));
    }

    @Tag (name = "ACCOUNT", description = "가계부")
    @Operation (summary = "가계부 항목 추가")
    @Parameters ()
    @PostMapping ("item")
    public ResponseEntity<ApiResponse<Boolean>> addAccountItem(@RequestBody AccountReqDto reqDto) throws Exception {
        //todo 유저 아이디 (하드코딩) 시큐리티 세션에서 꺼내 사용하는 방식으로 변경해야함.
        Long userId = 3L;
        return ApiResponse.success(accountService.insertAccountItem(userId, reqDto));
    }

    @Tag (name = "ACCOUNT", description = "가계부")
    @Operation (summary = "가계부 항목 수정 (전체)")
    @PutMapping ("item/{accountId}")
    public ResponseEntity<ApiResponse<Boolean>> putAccountItem(
            @PathVariable ("accountId") Long accountId,
            @RequestBody AccountReqDto reqDto) throws Exception {
        return ApiResponse.success(accountService.updateAccountItem(accountId, reqDto));
    }

    @Tag (name = "ACCOUNT", description = "가계부")
    @Operation (summary = "가계부 항목 수정 (일부)")
    @PatchMapping ("item/{accountId}")
    public ResponseEntity<ApiResponse<Boolean>> patchAccountItem(
            @PathVariable ("accountId") Long accountId,
            @RequestBody AccountReqDto reqDto) throws Exception {
        return ApiResponse.success(accountService.updateAccountItem(accountId, reqDto));
    }

    @Tag (name = "ACCOUNT", description = "가계부")
    @Operation (summary = "입력한 년월 에 해당하는 고정목록")
    @GetMapping ("fixedItem/{year}/{month}")
    public ResponseEntity<ApiResponse<List<AccountResDto>>> getFixedItem(@PathVariable ("year") int year,
                                                                         @PathVariable ("month") int month) {
        //todo 유저 아이디 (하드코딩) 시큐리티 세션에서 꺼내 사용하는 방식으로 변경해야함.
        Long userId = 3L;
        return ApiResponse.success(accountService.selectFixedItems(userId, year, month));
    }

    @Tag (name = "FAVORITE ACCOUNT", description = "가계부 즐겨찾기")
    @Operation (summary = "가계부 즐겨찾기 목록" ,description = " 카테고리 all 일 경우 전체")
    @GetMapping ("item/favor/{category}")
    public ResponseEntity<ApiResponse<List<FavorAccountResDto>>> getFavorItems(
            @PathVariable ("category") String category) {
        //todo 유저 아이디 (하드코딩) 시큐리티 세션에서 꺼내 사용하는 방식으로 변경해야함.
        Long userId = 3L;
        return ApiResponse.success(accountService.selectFavorAccountItems(userId, category));
    }

    @Tag (name = "FAVORITE ACCOUNT", description = "가계부 즐겨찾기")
    @Operation (summary = "가계부 즐겨찾기 추가")
    @PostMapping ("item/favor")
    public ResponseEntity<ApiResponse<Boolean>> addFavorItem(AccountReqDto reqDto) throws Exception {
        //todo 유저 아이디 (하드코딩) 시큐리티 세션에서 꺼내 사용하는 방식으로 변경해야함.
        Long userId = 3L;
        return ApiResponse.success(accountService.insertFavorAccountItem(userId, reqDto));
    }

    @Tag (name = "FAVORITE ACCOUNT", description = "가계부 즐겨찾기")
    @Operation (summary = "가계부 즐겨찾기 항목 수정 (전체)")
    @PutMapping ("item/favor/{favorAccountId}")
    public ResponseEntity<ApiResponse<Boolean>> putFavorAccount(
            @PathVariable Long favorAccountId,
            @RequestBody AccountReqDto reqDto) throws Exception {
        return ApiResponse.success(accountService.updateFavorAccountItem(favorAccountId, reqDto));
    }

    @Tag (name = "FAVORITE ACCOUNT", description = "가계부 즐겨찾기")
    @Operation (summary = "가계부 즐겨찾기 항목 수정 (일부)")
    @PatchMapping ("item/favor/{favorAccountId}")
    public ResponseEntity<ApiResponse<Boolean>> patchFavorAccount(
            @PathVariable Long favorAccountId,
            @RequestBody AccountReqDto reqDto) throws Exception {
        return ApiResponse.success(accountService.updateFavorAccountItem(favorAccountId, reqDto));
    }

    @Tag (name = "FAVORITE ACCOUNT", description = "가계부 즐겨찾기")
    @Operation (summary = "가계부 즐겨찾기 항목 삭제")
    @DeleteMapping ("item/favor/{favorAccountId}")
    public ResponseEntity<ApiResponse<Boolean>> removeFavorAccountItem(@PathVariable Long favorAccountId) {
        return ApiResponse.success(accountService.deleteFavorAccountItem(favorAccountId));
    }
}
