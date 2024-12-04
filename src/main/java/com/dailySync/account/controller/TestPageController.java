package com.dailySync.account.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestPageController {

    @GetMapping ("/account/test")
    public String testPageView() {
        return "redirect:/testPage/AccountTestPage.html";  // 정적 리소스를 리디렉션
    }
}