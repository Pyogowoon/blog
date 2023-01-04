package com.pyo.blog.test;


import com.pyo.blog.model.Board;
import com.pyo.blog.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReplyControllerTest {


    @Autowired
    private BoardRepository boardRepository;

    @GetMapping("/test/board/{id}")
    public Board getBoard(@PathVariable int id){
        return boardRepository.findById(id).get();  //jackson 라이브러리 (옵젝트를 json로 리턴) -> 모델의 getter를 호출
    }

}
