package com.pyo.blog.controller;


import com.pyo.blog.config.auth.PrincipalDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {



    @GetMapping({"","/"})
    public String index(@AuthenticationPrincipal PrincipalDetail principal){
       System.out.println("로그인 사용자 아이디 : "+principal.getUsername());
        return "index";  //yml에 지정해줬음

    }

}
