package com.lockers.outerpark.domain.concert.service;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

import javax.sql.DataSource;

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
import com.lockers.outerpark.domain.user.entity.User;
import com.lockers.outerpark.domain.user.entity.UserRole;
import com.lockers.outerpark.domain.user.repository.UserRepository;

@ActiveProfiles("test")
@SpringBootTest
public class ConcertIndexTest {

	@Autowired
	private ConcertRepository concertRepository;

	@Autowired
	private UserRepository userRepository;

	static boolean initialized = false;

	@Autowired
	private DataSource dataSource;

	@Tag("heavy")
	@Test
	void 콘서트_전체_조회_인덱싱_적용_테스트() throws Exception {

		//DB 직접 생성해서 테스트 시 사용
		// if (!initialized) {
		// 	setupConcertsJdbcBatch();
		// 	initialized = true;
		// }

		Pageable pageable = PageRequest.of(0, 5);

		long startTime = System.currentTimeMillis();

		Page<Concert> concerts = concertRepository.findUpcomingConcerts(
			LocalDate.now(), pageable);

		long endTime = System.currentTimeMillis();
		long durationMs = endTime - startTime;

		System.out.println("콘서트 인덱싱 적용 조회 시간: " + durationMs + "ms");

		assertEquals(5, concerts.getContent().size());
	}

	@Tag("heavy")
	@Test
	void 콘서트_전체_조회_인덱싱_미적용_테스트() throws Exception {

		//DB 직접 생성해서 테스트 시 사용
		// if (!initialized) {
		// 	setupConcertsJdbcBatch();
		// 	initialized = true;
		// }

		Pageable pageable = PageRequest.of(0, 5);

		long startTime = System.currentTimeMillis();

		Page<Concert> concerts = concertRepository.findUpcomingConcerts(
			LocalDate.now(), pageable);

		long endTime = System.currentTimeMillis();
		long durationMs = endTime - startTime;

		System.out.println("콘서트 인덱싱 미적용 조회 시간: " + durationMs + "ms");

		assertEquals(5, concerts.getContent().size());
	}

	void setupConcertsJdbcBatch() throws Exception {
		try (Connection conn = dataSource.getConnection()) {
			conn.setAutoCommit(false);

			String sql = "INSERT INTO concerts (writer_id, title, running_time, price, limit_age, performance_date, is_deleted) VALUES (?, ?, ?, ?, ?, ?, ?)";

			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				final int TOTAL_CONCERTS = 300_000;
				final int BATCH_SIZE = 1000;

				Long writerId = createTestUserAndGetId();

				for (int i = 0; i < TOTAL_CONCERTS; i++) {
					int randomDay = (i % 100 == 0)
						? ThreadLocalRandom.current().nextInt(0, 30)
						: -ThreadLocalRandom.current().nextInt(1, 365);

					ps.setLong(1, writerId);
					ps.setString(2, "테스트 콘서트 " + i);
					ps.setInt(3, 90 + (i % 30));
					ps.setInt(4, 30000 + (i % 5000));
					ps.setInt(5, 12 + (i % 3) * 6);
					ps.setDate(6, java.sql.Date.valueOf(LocalDate.now().plusDays(randomDay)));
					ps.setBoolean(7, i % 5 != 0); // soft delete 여부

					ps.addBatch();

					if (i % BATCH_SIZE == 0 && i > 0) {
						ps.executeBatch();
						conn.commit();
					}
				}

				ps.executeBatch();
				conn.commit();
			}
		}
	}

	private Long createTestUserAndGetId() {
		userRepository.deleteAll();

		User user = userRepository.save(
			new User(
				"testuser@example.com",
				"test",
				LocalDate.of(1995, 5, 15),
				"Password123*",
				10000L,
				UserRole.ADMIN
			)
		);
		return user.getId();
	}

}
