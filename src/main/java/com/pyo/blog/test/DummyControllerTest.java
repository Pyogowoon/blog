package com.pyo.blog.test;


import com.pyo.blog.model.RoleType;
import com.pyo.blog.model.User;
import com.pyo.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Supplier;

@RestController
public class DummyControllerTest {


    @Autowired  //의존성주입(DI)
    private UserRepository userRepository;

    @DeleteMapping("/dummy/user/{id}")
   public String delete(@PathVariable int id){
        try{
            userRepository.deleteById(id);
        }catch(EmptyResultDataAccessException e){
            return "삭제에 실패하였습니다. DB가 존재하지않습니다.";

        }

        return "삭제 되었습니다. id :" + id;
    }


    //save 함수는 id를 전달하지않을시 insert를 하고
    //save 함수는 id를 전달하고 해당 id에 대한 데이터 존재시 update를 실시
    //email , password 받아야함
    //@Pathvariable 은 url값으로 파라메터 받을때 사용
    // @RequestBody는 제이슨으로 받을때 사용
    @Transactional   // 함수 종료시에 자동 commit
    @PutMapping("/dummy/user/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User requestUser){ //json 데이터를 요청 --> java object로 변환해서 받아줌
               System.out.println("id : " + id);
               System.out.println("password :" + requestUser.getPassword());
               System.out.println("email :" + requestUser.getEmail());

                User user = userRepository.findById(id).orElseThrow(() -> {
                    return new IllegalArgumentException("수정에 실패하였습니다.");
                });
                user.setPassword(requestUser.getPassword());
                user.setEmail(requestUser.getEmail());

//               userRepository.save(user);

            //더티 체킹 이란?
               return user;
    }

    @GetMapping("/dummy/users")
    public List<User> list(){

    return userRepository.findAll();
    }

    @GetMapping("/dummy/user")
    public List<User> pageList(@PageableDefault(size=2, sort="id", direction = Sort.Direction.DESC) Pageable pageable){
        Page<User> pagingUser = userRepository.findAll(pageable);

        List<User> users = pagingUser.getContent();
            return users;
    }

    //{id} 주소로 파라미터를 전달받을 수 있음
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id){
       User user = userRepository.findById(id).orElseThrow(()->{
           return new IllegalArgumentException("해당 사용자는 없습니다2.");
       });
       //요청 == 웹 브라우저
       //user 객체 = 자바 오브젝트
        //그렇다면 웹브라우저가 이해할수있도록 JSON 으로 변환해야합니다
        //스프링 부트는 MessageConverter 라는 애가 응답시 자동 작동
        //만약 자바 오브젝트를 리턴하게 되면 MC가 Jackson 라이브러리 호출해서
        //user 오브젝트를 json으로 변환해서 브라우저에게 던져줌
        return user;
    }

    @PostMapping("/dummy/join")
    public String join(User user){
    System.out.println("username : "+ user.getUsername() +" password : " + user.getPassword() + " email : " + user.getEmail());
    System.out.println("id =" + user.getId());
    System.out.println("time=" + user.getCreateDate());
    System.out.println("role :" + user.getRole());
    System.out.println("createDate :" + user.getRole());

    user.setRole(RoleType.USER);
    userRepository.save(user);
        return "회원가입이 완료되었습니다.";
    }
}
