package com.pyo.blog.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//인증이 안된 사용자들이 출입할수 있는 경로를 /auth/*** 허용
// 그냥 주소가 / 이면 index.jsp 허용
// static 이하에 있는 /js/**,/css/** , /image/** 허용
//즉 인증이 필요없는 곳은 auth를 붙일것

@Controller
public class UserContorller {

    @GetMapping("/auth/loginForm")
    public String loginForm() {
        return "user/loginForm";

    }

    @GetMapping("/auth/joinForm")
    public String joinForm() {

        return "user/joinForm";
    }
}
