package com.pyo.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false , length = 100)
    private String title;

    @Lob // 대용량 데이터
    private String content;  // 차후 섬머노트 라이브러리 사용


    private int count; // 조회수

    @ManyToOne(fetch = FetchType.EAGER) // Board = many, user = One 한명의 유저가 여러개의 게시글을 쓸수있다
    @JoinColumn(name="userId") //포린키로 조인해온 DB아이디 라고 생각하면됨
    private User user; //DB는 오브젝트를 저장할수 없다 그래서 FK사용
    //그러나 자바는 오브젝트를 저장할 수 있다.

    @OneToMany(mappedBy="board" , fetch = FetchType.EAGER) // mappedBy = 연관관계의 주인이 아니다(난 FK가 아니에요) DB에 칼럼 만들지않음.
    // board == reply에 있는 필드 board; 임
    private List<Reply> reply; //따로 Joincolumn이 필요없다.
    // 그 이유는 조인을 할건 아니고 어차피 따로 자바 오브젝트로만 활용할거니까

    @CreationTimestamp
    private Timestamp createDate;
}
