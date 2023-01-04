package com.pyo.blog.repository;

import com.pyo.blog.dto.ReplySaveRequestDto;
import com.pyo.blog.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {

    @Modifying  // int만 반환 가능함
    @Query(value="INSERT INTO reply(userId, boardId, content, createDate) values(?1, ?2, ?3,now())", nativeQuery = true)
    int mSave(int userId, int boardId, String content);

}
