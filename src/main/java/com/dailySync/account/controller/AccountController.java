package com.dailySync.account.controller;

import com.dailySync.account.dto.AccountReqDto;
import com.dailySync.account.dto.AccountResDto;
import com.dailySync.account.dto.FavorAccountResDto;
import com.dailySync.account.service.AccountService;
import com.dailySync.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping ("api/account")
public class AccountController {
    private final AccountService accountService;

    /**
     * 날짜별 가계부 항목 조회
     */
    @Tag (name = "ACCOUNT", description = "가계부")
    @Operation (summary = "해당 날짜의 가계부 목록")
    @GetMapping ("items/date/{date}")
    public ResponseEntity<ApiResponse<List<AccountResDto>>> getAccountItemsByDate(
            @Parameter (description = "날짜 YYYY-MM-DD")
            @PathVariable ("date") LocalDate date) {
        //todo 유저 아이디 (하드코딩) 시큐리티 세션에서 꺼내 사용하는 방식으로 변경해야함.
        Long userId = 3L;
        return ApiResponse.success(accountService.findAccountsByDate(userId, date));
    }

    /**
     * 월 단위 날짜별 가계부 항목 합산 조회
     */
    @Tag (name = "ACCOUNT", description = "가계부")
    @Operation (summary = "해당 월에 각 날짜별 수입,지출의 합산")
    @GetMapping ("items/month/{year}/{month}")
    public ResponseEntity<?> getAccountItemsSummaryByMonth(
            @Parameter (description = "년")
            @PathVariable ("year") int year,
            @Parameter (description = "월")
            @PathVariable ("month") int month) {
        //todo 유저 아이디 (하드코딩) 시큐리티 세션에서 꺼내 사용하는 방식으로 변경해야함.
        Long userId = 3L;
        return ApiResponse.success(accountService.findAccountsByMonth(userId, year, month));
    }

    /**
     * 날짜 가계부 합산 조회
     */
    @Tag (name = "ACCOUNT", description = "가계부")
    @Operation (summary = "해당 날짜의 수입,지출의 합산")
    @GetMapping ("items/sum/{date}")
    public ResponseEntity<?> getAccountItemsSummaryByDate(
            @Parameter (description = "날짜 ex.'2024-12-12'")
            @PathVariable ("date") LocalDate date) {
        Long userId = 3L;
        return ApiResponse.success(accountService.findSumAccountByDate(userId, date));
    }

    /**
     * 고정 항목 조회
     */
    @Tag (name = "ACCOUNT", description = "가계부")
    @Operation (summary = "입력한 년월 에 해당하는 고정목록")
    @GetMapping ("items/fixed/{year}/{month}")
    public ResponseEntity<ApiResponse<List<AccountResDto>>> getFixedAccountItems(
            @Parameter (description = "년")
            @PathVariable ("year") int year,
            @Parameter (description = "월")
            @PathVariable ("month") int month) {
        //todo 유저 아이디 (하드코딩) 시큐리티 세션에서 꺼내 사용하는 방식으로 변경해야함.
        Long userId = 3L;
        return ApiResponse.success(accountService.findFixedAccounts(userId, year, month));
    }

    /**
     * 즐겨찾기 항목 조회
     */
    @Tag (name = "FAVORITE ACCOUNT", description = "가계부 즐겨찾기")
    @Operation (summary = "가계부 즐겨찾기 목록", description = " 카테고리 all 일 경우 전체")
    @GetMapping ("items/favor/{category}")
    public ResponseEntity<ApiResponse<List<FavorAccountResDto>>> getFavorAccountItems(
            @Parameter (description = "카테고리 'ALL' = 전부") @PathVariable ("category") String category) {
        //todo 유저 아이디 (하드코딩) 시큐리티 세션에서 꺼내 사용하는 방식으로 변경해야함.
        Long userId = 3L;
        return ApiResponse.success(accountService.findFavorAccountItems(userId, category));
    }

    /**
     * 새로운 가계부 항목 추가
     */
    @Tag (name = "ACCOUNT", description = "가계부")
    @Operation (summary = "가계부 항목 추가", description = "새로운 가계부 항목을 추가합니다.")
    @PostMapping ("items")
    public ResponseEntity<ApiResponse<Boolean>> createAccountItem(
            @RequestBody AccountReqDto reqDto) throws Exception {
        //todo 유저 아이디 (하드코딩) 시큐리티 세션에서 꺼내 사용하는 방식으로 변경해야함.
        Long userId = 3L;
        return ApiResponse.success(accountService.createAccountItem(userId, reqDto));
    }

    /**
     * 즐겨찾기 항목 추가
     */
    @Tag (name = "FAVORITE ACCOUNT", description = "가계부 즐겨찾기")
    @Operation (summary = "가계부 즐겨찾기 추가")
    @PostMapping ("items/favor")
    public ResponseEntity<ApiResponse<Boolean>> createFavorAccountItem(
            @RequestBody AccountReqDto reqDto) throws Exception {
        //todo 유저 아이디 (하드코딩) 시큐리티 세션에서 꺼내 사용하는 방식으로 변경해야함.
        Long userId = 3L;
        return ApiResponse.success(accountService.createFavorAccountItem(userId, reqDto));
    }

    /**
     * 가계부 항목 전체 수정
     */
    @Tag (name = "ACCOUNT", description = "가계부")
    @Operation (summary = "가계부 항목 수정 (전체)")
    @PutMapping ("items/{accountId}")
    public ResponseEntity<ApiResponse<Boolean>> updateAccountItem(
            @Parameter (description = "가계부 항목 아이디") @PathVariable ("accountId") Long accountId,
            @RequestBody AccountReqDto reqDto) throws Exception {
        return ApiResponse.success(accountService.updateAccountItem(accountId, reqDto));
    }

    /**
     * 가계부 항목 부분 수정
     */
    @Tag (name = "ACCOUNT", description = "가계부")
    @Operation (summary = "가계부 항목 수정 (일부)")
    @PatchMapping ("items/{accountId}")
    public ResponseEntity<ApiResponse<Boolean>> partiallyUpdateAccountItem(
            @Parameter (description = "가계부 항목 아이디")
            @PathVariable ("accountId") Long accountId,
            @RequestBody AccountReqDto reqDto) throws Exception {
        return ApiResponse.success(accountService.updateAccountItem(accountId, reqDto));
    }

    /**
     * 즐겨찾기 항목 전체 수정
     */
    @Tag (name = "FAVORITE ACCOUNT", description = "가계부 즐겨찾기")
    @Operation (summary = "가계부 즐겨찾기 항목 수정 (전체)")
    @PutMapping ("items/favor/{favorAccountId}")
    public ResponseEntity<ApiResponse<Boolean>> updateFavorAccountItem(
            @Parameter (description = "즐겨찾기 항목 아이디") @PathVariable Long favorAccountId,
            @RequestBody AccountReqDto reqDto) throws Exception {
        return ApiResponse.success(accountService.updateFavorAccountItem(favorAccountId, reqDto));
    }

    /**
     * 즐겨찾기 항목 부분 수정
     */
    @Tag (name = "FAVORITE ACCOUNT", description = "가계부 즐겨찾기")
    @Operation (summary = "가계부 즐겨찾기 항목 수정 (일부)")
    @PatchMapping ("items/favor/{favorAccountId}")
    public ResponseEntity<ApiResponse<Boolean>> partiallyUpdateFavorAccountItem(
            @Parameter (description = "즐겨찾기 항목 아이디")
            @PathVariable Long favorAccountId,
            @RequestBody AccountReqDto reqDto) throws Exception {
        return ApiResponse.success(accountService.updateFavorAccountItem(favorAccountId, reqDto));
    }

    /**
     * 가계부 항목 삭제
     */
    @Tag (name = "ACCOUNT", description = "가계부")
    @Operation (summary = "가계부 항목 제거", description = "가계부 항목을 제거합니다.")
    @DeleteMapping ("items/{accountId}")
    public ResponseEntity<ApiResponse<Boolean>> deleteAccountItem(
            @Parameter (description = "가계부 항목 아이디")
            @PathVariable ("accountId") Long accountId) {
        return ApiResponse.success(accountService.deleteAccountItem(accountId));
    }

    /**
     * 즐겨찾기 항목 삭제
     */
    @Tag (name = "FAVORITE ACCOUNT", description = "가계부 즐겨찾기")
    @Operation (summary = "가계부 즐겨찾기 항목 삭제")
    @DeleteMapping ("items/favor/{favorAccountId}")
    public ResponseEntity<ApiResponse<Boolean>> deleteFavorAccountItem(
            @Parameter (description = "즐겨찾기 항목 아이디")
            @PathVariable Long favorAccountId) {
        return ApiResponse.success(accountService.deleteFavorAccountItem(favorAccountId));
    }
}
