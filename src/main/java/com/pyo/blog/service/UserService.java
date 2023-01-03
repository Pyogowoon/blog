package com.pyo.blog.service;


import com.pyo.blog.config.auth.PrincipalDetail;
import com.pyo.blog.model.RoleType;
import com.pyo.blog.model.User;
import com.pyo.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


//스프링이 컴포넌트 스캔을 통해서 bean에 등록을 해줌. IoC를 해준다.
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;





    @Transactional //전체가 성공시 commit 실패시 rollback
    public void 회원가입(User user){
        String rawPassword = user.getPassword(); // 원문
        String encPassword = encoder.encode(rawPassword);
        user.setPassword(encPassword);
        user.setRole(RoleType.USER); // role은 넣어줘야함
        userRepository.save(user);

    }

    @Transactional
    public void 회원수정(User user){
    //수정시에는 영속성 컨텍스트에 User 오브젝트를 영속화 시킨 후
        //그 User 오브젝트를 수정
        User persistence = userRepository.findById(user.getId()).orElseThrow(()->{
            return new IllegalArgumentException("아이디를 찾지 못했습니다");
        });
    //validate 체크 => oauth 필드에 값이 없으면 수정 가능
        if(persistence.getOauth() == null || persistence.getOauth().equals("")){
        String rawPassword = user.getPassword();
        String encPassword = encoder.encode(rawPassword);
        persistence.setPassword(encPassword);
        persistence.setEmail(user.getEmail());
        }
        //회원수정 함수 종료시 = 서비스 종료 = 트랜잭션의 종료 = commit이 자동으로
        // 영속화된 persistence 객체의 변화가 감지되면 더티체킹이 되어 update문 날려줌


    }

    @Transactional(readOnly = true)
    public User 회원찾기(String username){

        User user =
       userRepository.findByUsername(username).orElseGet(()->{
           //orElseGet -> 없으면 빈 객체 리턴
           return new User();
       });
            return user;
        }

    }

            //옛날 방식의 로그인
//    @Transactional(readOnly = true) //select할떄 트랜잭션 시작, 서비스 종료시 트랜잭션 종료
//    // 사용하는 이유는 정합성을 보장하기 위해서
//    public User 로그인(User user){
//        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//
//
//    }

