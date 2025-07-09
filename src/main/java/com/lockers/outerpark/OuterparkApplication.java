package com.lockers.outerpark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.lockers.outerpark.domain.seat.entity.Seat;
import com.lockers.outerpark.domain.seat.repository.SeatRepository;

@EnableJpaAuditing
@SpringBootApplication
public class OuterparkApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(OuterparkApplication.class, args);

		// 좌석 마스터 데이터 생성
		// ddl-auto: create 설
		SeatRepository seatRepository = context.getBean(SeatRepository.class);

		if (seatRepository.count() == 0) {
			for (int i = 1; i <= 100; i++) {
				seatRepository.save(new Seat(i));
			}
			System.out.println("좌석 1~100개 데이터 생성 완료");
		} else {
			System.out.println("좌석 이미 존재");
		}
	}

}
