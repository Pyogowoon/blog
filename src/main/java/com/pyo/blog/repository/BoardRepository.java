package com.pyo.blog.repository;

import com.pyo.blog.model.Board;
import com.pyo.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // 생략가능
public interface BoardRepository extends JpaRepository<Board, Integer> {




}
