package com.lockers.outerpark.domain.concert.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lockers.outerpark.domain.concert.entity.Concert;

public interface ConcertRepository extends JpaRepository<Concert, Long> {

	Optional<Concert> findByIdAndIsDeletedFalse(Long id);

	/**
	 *삭제되지 않았으며, 주어진 날짜 이후에 열리는 콘서트들을 공연일과 ID 기준으로 오름차순 정렬하여 조회
	 * @param now 공연일 기준 필터 날짜 (이 날짜 이후의 공연만 조회됨)
	 * @param pageable 페이징 및 페이지 크기 설정
	 * @return 조건에 맞는 콘서트 목록을 포함하는 페이지 객체
	 */
	@Query("""
		    SELECT c FROM Concert c
		    WHERE c.isDeleted = false AND c.performanceDate >= :now
		    ORDER BY c.performanceDate ASC, c.id ASC
		""")
	Page<Concert> findUpcomingConcerts(@Param("now") LocalDate now, Pageable pageable);

}
