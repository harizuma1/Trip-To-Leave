package com.study.trip.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.study.trip.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
