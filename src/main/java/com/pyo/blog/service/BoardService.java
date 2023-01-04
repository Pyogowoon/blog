package com.pyo.blog.service;


import com.pyo.blog.dto.ReplySaveRequestDto;
import com.pyo.blog.model.Board;
import com.pyo.blog.model.Reply;
import com.pyo.blog.model.RoleType;
import com.pyo.blog.model.User;
import com.pyo.blog.repository.BoardRepository;
import com.pyo.blog.repository.ReplyRepository;
import com.pyo.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


//스프링이 컴포넌트 스캔을 통해서 bean에 등록을 해줌. IoC를 해준다.
@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void 글쓰기(Board board, User user) { // title , content
        board.setCount(0);
        board.setUser(user);
        boardRepository.save(board);


    }

    @Transactional(readOnly = true)
    public Page<Board> 글목록(Pageable pageable) {
        return boardRepository.findAll(pageable);

    }


    @Transactional(readOnly = true)
    public Board 글상세보기(int id) {
        return boardRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
        });

    }

    @Transactional
    public void 글삭제하기(int id) {
        boardRepository.deleteById(id);
        System.out.println("삭제서비스 완료");
    }

    @Transactional
    public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto) {
//        User user = userRepository.findById(replySaveRequestDto.getUserId()).orElseThrow(() -> {
////            return new IllegalArgumentException("유저 찾기 실패: 아이디를 찾을수없습니다.");
////        });
////
////
////        Board board = boardRepository.findById(replySaveRequestDto.getBoardId()).orElseThrow(() -> {
////            return new IllegalArgumentException("글 찾기 실패: 아이디를 찾을수없습니다.");
////        });  replyRepository에 쿼리를 추가함으로서 영속화가 필요없어짐

//        Reply reply = Reply.builder()
//                .user(user)
//                .board(board)
//                .content(replySaveRequestDto.getContent())
//                .build(); //위와 동일

        replyRepository.mSave(replySaveRequestDto.getUserId(),replySaveRequestDto.getBoardId(),replySaveRequestDto.getContent());


    }


    @Transactional
    public void 댓글삭제(int replyId){
        replyRepository.deleteById(replyId);

    }
}
