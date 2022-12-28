package com.pyo.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempControllerTest {

    @GetMapping("/temp/home")
    public String tempHome(){
        System.out.println("tempHome()");
        //파일 리턴 기본경로 : src/main/resources/static 그래서 리턴 앞에 / 슬래쉬붙임
        return "/home.html";
    }
    @GetMapping("/temp/img")
    public String tempImg(){
        return "/a.png";
    }
    @GetMapping("/temp/jsp")
    public String tempJsp(){
        //yml 에서 prefix를 WEB-INF/views 로 지정했고
        // suffix 에서 .jsp를 지정했기 때문에
        //풀 경로는 WEB-INF/views/test.jsp 가 됌
        return "test";
    }

}
