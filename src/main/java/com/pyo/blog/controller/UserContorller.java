package com.pyo.blog.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pyo.blog.model.KakaoProfile;
import com.pyo.blog.model.OAuthToken;
import com.pyo.blog.model.User;
import com.pyo.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;


//인증이 안된 사용자들이 출입할수 있는 경로를 /auth/*** 허용
// 그냥 주소가 / 이면 index.jsp 허용
// static 이하에 있는 /js/**,/css/** , /image/** 허용
//즉 인증이 필요없는 곳은 auth를 붙일것

@Controller
public class UserContorller {

    @Value("${cos.key}")
    private String cosKey;


    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private UserService userService;

    @GetMapping("/auth/loginForm")
    public String loginForm() {
        return "user/loginForm";

    }


    @GetMapping("/auth/joinForm")
    public String joinForm() {

        return "user/joinForm";
    }

    @GetMapping("/user/updateForm")
    public String updateForm() {

        return "user/updateForm";
    }

    @GetMapping("/auth/kakao/callback")
    public String kakaoCallback(String code) { // Data를 리턴해주는 컨트롤러 함수

        //POST방식으로 key=value 데이터를 요청(카카오쪽으로)
        RestTemplate rt = new RestTemplate();  //이거 사용시 편하게 post요청 가능

        //HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "99df102fbad65429a53661f8958c3904");
        params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
        params.add("code", code);

        //HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        //Http 요청하기 - POST방식으로 - 그리고 reponse 변수의 응답 받음
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        //JSON타입을 Object에 담기 - Gson, Json Simple, ObjectMapper

        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oauthToken = null;
        try {
            oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        System.out.println("카카오 엑세스 토큰 : " + oauthToken.getAccess_token());

        //// 복사된 영역 ////
//POST방식으로 key=value 데이터를 요청(카카오쪽으로)
        RestTemplate rt2 = new RestTemplate();  //이거 사용시 편하게 post요청 가능

        //HttpHeader 오브젝트 생성
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");


        //HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 =
                new HttpEntity<>(headers2);

        //Http 요청하기 - POST방식으로 - 그리고 reponse 변수의 응답 받음
        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest2,
                String.class
        );

        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        // 이렇게 정보를 받으면 User 오브젝트 : model에 User에 담겨있는거
        // username , password , email 이 필요하고
        System.out.println("카카오 아이디(번호): " + kakaoProfile.getId());
        System.out.println("카카오 이메일 " + kakaoProfile.getKakao_account().getEmail());

        System.out.println("블로그서버 유저네임 : " + kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId());
        System.out.println("블로그서버 이메일 : " + kakaoProfile.getKakao_account().getEmail());

        // 이건 임시비밀번호임. 아무래도 패스워드가 없으면 안되므로
        
        //UUID -> 중복되지않는 어떤 특정값을 만들어내는 알고리즘
//        UUID garbagePassword = UUID.randomUUID();
        System.out.println("블로그서버 패스워드 :" + cosKey);

        User kakaoUser = User.builder()
                .username(kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId())
                .password(cosKey)
                .email(kakaoProfile.getKakao_account().getEmail())
                .oauth("kakao")
                .build();
        
        User orginUser = userService.회원찾기(kakaoUser.getUsername());

        if(orginUser.getUsername() == null){
            System.out.println("기존회원이 아니기에 자동회원가입 진행");
            userService.회원가입(kakaoUser);
        }
            //로그인 처리
            System.out.println("자동 로그인 진행");
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(),cosKey));
        SecurityContextHolder.getContext().setAuthentication(authentication);


        //가입자 혹은 비가입자 체크 해서 처리해야함.

        return "redirect:/";
    }
}
