package com.pyo.blog.repository;

import com.pyo.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // 생략가능
public interface UserRepository extends JpaRepository<User, Integer> {

    //SELECT * FROM user WHERE username =1?;
    Optional<User> findByUsername(String username);


}

        //옛날방식 로그인
//    User findByUsernameAndPassword(String username, String password);

// 위의 FindByUsernameAndPassword 함수와 똑같은 함수임. 이런식으로 네이티브쿼리로 지정해서 불러오기도 가능함
//    @Query(value="SELECT * FROM user WHERE username =?1 AND password = ?2", nativeQuery = true)
//    User login(String username, String password);

