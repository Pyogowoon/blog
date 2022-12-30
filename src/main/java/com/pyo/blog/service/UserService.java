package com.pyo.blog.service;


import com.pyo.blog.model.User;
import com.pyo.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


//스프링이 컴포넌트 스캔을 통해서 bean에 등록을 해줌. IoC를 해준다.
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional //전체가 성공시 commit 실패시 rollback
    public int 회원가입(User user){
        try{
        userRepository.save(user);
        return 1;
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Userservice : 회원가입()" + e.getMessage());
        }
        return -1;
    }

    @Transactional(readOnly = true) //select할떄 트랜잭션 시작, 서비스 종료시 트랜잭션 종료
    // 사용하는 이유는 정합성을 보장하기 위해서
    public User 로그인(User user){
        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());


    }
}
