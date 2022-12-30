package com.pyo.blog.controller.api;


import com.pyo.blog.dto.ResponseDto;
import com.pyo.blog.model.RoleType;
import com.pyo.blog.model.User;
import com.pyo.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class UserApiController {

    @Autowired
    private UserService userService;


    @PostMapping("/api/user")
    public ResponseDto<Integer> save(@RequestBody User user) {

        System.out.println("UserApiController : save 호출됨");
        //실제로 DB에 insert를 하고 아래에서 return하는 과정

        user.setRole(RoleType.USER); // role은 넣어줘야함
        int result = userService.회원가입(user);

        return new ResponseDto<Integer>(HttpStatus.OK.value(), result);
    }



    @PostMapping("/api/user/login")
    public ResponseDto<Integer> login(@RequestBody User user , HttpSession session) {
        System.out.println("UserApiController : login 호출됨");
        User principal = userService.로그인(user);  //principal (접근주체)

        if(principal != null){
            session.setAttribute("principal", principal);
        }
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

}
