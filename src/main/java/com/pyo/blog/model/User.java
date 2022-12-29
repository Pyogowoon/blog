package com.pyo.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;

//ORM -> JAVA OBJECT -> 테이블로 매핑해주는 기술

@Data
//@DynamicInsert  insert시 null인 값 제외
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity  // User클래스가 DB화 됨 mysql에 테이블이 생성됨.
public class User {

    @Id  // 프라이머리키
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; //auto_increment

    @Column(nullable = false, length = 30 , unique = true)
    private String username;

    @Column(nullable = false, length = 100) // 길게주는 이유 = 암호화
    private String password;

    @Column(nullable = false , length = 50)
    private String email;


    //DB엔 RoleType이 없으므로 이넘 임을 알려줘야함
//    @ColumnDefault("'user'")
    @Enumerated(EnumType.STRING)
    private RoleType role; // Enum을 쓰는게 좋음. admin인지 user인지 manager 인지 구분하고자

    @CreationTimestamp // 시간이 자동입력
    private Timestamp createDate;
}
