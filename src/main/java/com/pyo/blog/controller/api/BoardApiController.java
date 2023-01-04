package com.pyo.blog.controller.api;


import com.pyo.blog.config.auth.PrincipalDetail;
import com.pyo.blog.dto.ReplySaveRequestDto;
import com.pyo.blog.dto.ResponseDto;
import com.pyo.blog.model.Board;
import com.pyo.blog.model.Reply;
import com.pyo.blog.model.User;
import com.pyo.blog.service.BoardService;
import com.pyo.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class BoardApiController {

    @Autowired
    private BoardService boardService;


    @PostMapping("/api/board")
    public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) {
    boardService.글쓰기(board,principal.getUser());
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
    }

    @DeleteMapping("/api/board/{id}")
    public ResponseDto<Integer> deleteById(@PathVariable int id){
        boardService.글삭제하기(id);
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);

    }
    
    //데이터를 받을때 컨트롤러에서 dto를 만들어서 받는게 좋다.
    //여기서 따로 이용하지않은 이유는 규모가 작아서인데 규모가 커질수록 아래처럼 OBJECT로 받아오는게 좋은방법은 아님
    @PostMapping("/api/board/{boardId}/reply")
    public ResponseDto<Integer> replySave(@RequestBody ReplySaveRequestDto replySaveRequestDto) {


        boardService.댓글쓰기(replySaveRequestDto);
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
    }

    //boardId는 주소 만들려고 넣은것이고 replyId는 이걸로 댓글삭제 하기위해서
    @DeleteMapping("/api/board/{boardId}/{replyId}")
    public ResponseDto<Integer> replyDelete(@PathVariable int replyId){

        boardService.댓글삭제(replyId);
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);

    }

}
