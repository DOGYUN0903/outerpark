package com.lockers.outerpark.domain.concert.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import com.lockers.outerpark.domain.concert.entity.Concert;
import com.lockers.outerpark.domain.concert.repository.ConcertRepository;

@ActiveProfiles("test")
@SpringBootTest
public class ConcertIndexTest {

	@Autowired
	private ConcertRepository concertRepository;

	@Tag("heavy")
	@Test
	void 콘서트_전체_조회_인덱싱_적용_테스트() {

		Pageable pageable = PageRequest.of(0, 5);

		long startTime = System.currentTimeMillis();

		Page<Concert> concerts = concertRepository.findUpcomingConcerts(
			LocalDate.now(), pageable);

		long endTime = System.currentTimeMillis();
		long durationMs = endTime - startTime;

		System.out.println("콘서트 인덱싱 적용 조회 시간: " + durationMs + "ms");

		assertEquals(0, concerts.getContent().size());
	}

	@Tag("heavy")
	@Test
	void 콘서트_전체_조회_인덱싱_미적용_테스트() {

		Pageable pageable = PageRequest.of(0, 5);

		long startTime = System.currentTimeMillis();

		Page<Concert> concerts = concertRepository.findUpcomingConcerts(
			LocalDate.now(), pageable);

		long endTime = System.currentTimeMillis();
		long durationMs = endTime - startTime;

		System.out.println("콘서트 인덱싱 미적용 조회 시간: " + durationMs + "ms");

		assertEquals(0, concerts.getContent().size());
	}
}
