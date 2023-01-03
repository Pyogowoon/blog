package com.pyo.blog.controller.api;


import com.pyo.blog.config.auth.PrincipalDetail;
import com.pyo.blog.dto.ResponseDto;
import com.pyo.blog.model.RoleType;
import com.pyo.blog.model.User;
import com.pyo.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class UserApiController {

    @Autowired
    private UserService userService;


    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> save(@RequestBody User user) {

        System.out.println("UserApiController : save 호출됨");
        //실제로 DB에 insert를 하고 아래에서 return하는 과정
        userService.회원가입(user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @PutMapping("/user")
    public ResponseDto<Integer> update(@RequestBody User user) {

        userService.회원수정(user);
        //여기서는 트랜잭션이 종료되기 때문에 DB에 값은 변경됐음
        //하지만 세션값은 아직 변경안됨, 직접 세션값 변경해줘야함
        //세션 등록
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //오류수정한것 : username을 ajax로 안받고 username을 찾고있었음.

        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }


    //아래는 옛날방식
//    @PostMapping("/api/user/login")
//    public ResponseDto<Integer> login(@RequestBody User user , HttpSession session) {
//        System.out.println("UserApiController : login 호출됨");
//        User principal = userService.로그인(user);  //principal (접근주체)
//
//        if(principal != null){
//            session.setAttribute("principal", principal);
//        }
//        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
//    }

}
