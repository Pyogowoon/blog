package com.pyo.blog.test;


import org.springframework.web.bind.annotation.*;

@RestController
public class HttpControllerTest {

    private static final String TAG = "HttpControllerTest";

    @GetMapping("/http/lombok")
    public String lombokTest(){
        Member m = Member.builder().username("ssar").password("1234").email("ssar@nate.com").build();
        System.out.println(TAG +"geeter :" +m.getUsername());
        m.setUsername("cos");
        System.out.println(TAG +"setter :" +m.getUsername());

        return "lombok test 완료";
    }


//인터넷 브라우저 요청은 무조건 get
    @GetMapping("/http/get")  //select
    public String getTest(Member m){

        return "get 요청 = " +m.getId()+",username = "+m.getUsername()+"  email ="+m.getEmail();

    }

    @PostMapping("/http/post")  //insert
    public String postTest(@RequestBody Member m){
        return "post  요청  id= " +m.getId()+",username = "+m.getUsername()+"password = " +m.getPassword() + "  email ="+m.getEmail();

    }
    @PutMapping("/http/put")   //update
    public String putTest(@RequestBody Member m){
        return "post  요청  id= " +m.getId()+",username = "+m.getUsername()+"password = " +m.getPassword() + "  email ="+m.getEmail();

    }

    @DeleteMapping("/http/delete") // delete
    public String deleteTest(){
        return "delete 요청";
    }
}
