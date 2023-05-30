package com.study.trip.domain.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ReviewRepository extends JpaRepository<Review, Long> {

	@Modifying
	@Query("update Review p set p.count = p.count + 1 where p.id = :id")
	int updateCount(Long id);

	Page<Review> findByTitleContainingOrContentContaining(
		@Param("title") String title, @Param("content") String content, Pageable pageable);
}
