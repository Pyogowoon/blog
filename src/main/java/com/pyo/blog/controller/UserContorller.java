package com.pyo.blog.controller;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;


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

    @GetMapping("/user/updateForm")
    public String updateForm(){

        return "user/updateForm";
    }

    @GetMapping("/auth/kakao/callback")
    public @ResponseBody String kakaoCallback(String code){ // Data를 리턴해주는 컨트롤러 함수

       //POST방식으로 key=value 데이터를 요청(카카오쪽으로)
        RestTemplate rt = new RestTemplate();  //이거 사용시 편하게 post요청 가능
        
        //HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type" , "authorization_code");
        params.add("client_id" , "99df102fbad65429a53661f8958c3904");
        params.add("redirect_uri" , "http://localhost:8000/auth/kakao/callback");
        params.add("code" , code);

        //HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
        new HttpEntity<>(params,headers);

         //Http 요청하기 - POST방식으로 - 그리고 reponse 변수의 응답 받음
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );
        return "카카오 토큰 요청완료 : 코드값 :" + response;
    }
}
