package com.study.trip.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.study.trip.domain.entity.Board;


public interface BoardRepository extends JpaRepository<Board, Long> {

    /* 유효성 검사 - 중복 체크
     * 중복 : true
     * 중복이 아닌 경우 : false
     */
//    boolean existsByStudy_project(String study_project);
//    boolean existsByNickname(String nickname);
//    boolean existsByEmail(String email);


	List<Board> findByTitleContaining(String searchKeyword); // 검색 키워드 찾는 메소드

}
