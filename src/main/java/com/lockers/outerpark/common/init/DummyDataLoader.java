package com.lockers.outerpark.common.init;

import java.time.LocalDate;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.lockers.outerpark.domain.concert.entity.Concert;
import com.lockers.outerpark.domain.concert.repository.ConcertRepository;
import com.lockers.outerpark.domain.user.entity.User;
import com.lockers.outerpark.domain.user.entity.UserRole;
import com.lockers.outerpark.domain.user.repository.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DummyDataLoader implements ApplicationRunner {

	private final ConcertRepository concertRepository;
	private final UserRepository userRepository;
	private final EntityManager entityManager;

	@Override
	@Transactional
	public void run(ApplicationArguments args) throws Exception {
		if (concertRepository.count() > 0) {
			System.out.println("이미 콘서트 있음. 삽입 생략");
			return;
		}

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

		final int BATCH_SIZE = 100;

		for (int i = 0; i < 500; i++) {
			Concert concert = Concert.builder()
				.writer(user)
				.title("테스트 콘서트 " + i)
				.runningTime(90 + (i % 30))
				.price(30000 + (i % 5000))
				.limitAge(12 + (i % 3) * 6)
				.performanceDate(LocalDate.now().plusDays(i % 30))
				.build();

			entityManager.persist(concert);

			if (i % BATCH_SIZE == 0 && i > 0) {
				entityManager.flush();
				entityManager.clear();
			}
		}

		entityManager.flush();
		entityManager.clear();
	}
}
