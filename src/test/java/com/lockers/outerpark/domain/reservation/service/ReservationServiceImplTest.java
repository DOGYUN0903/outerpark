package com.lockers.outerpark.domain.reservation.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import com.lockers.outerpark.domain.concert.dto.RegisterConcertRequest;
import com.lockers.outerpark.domain.concert.entity.Concert;
import com.lockers.outerpark.domain.concert.repository.ConcertRepository;
import com.lockers.outerpark.domain.reservation.dto.request.ReservationRequest;
import com.lockers.outerpark.domain.reservation.entity.Reservation;
import com.lockers.outerpark.domain.reservation.repository.ReservationRepository;
import com.lockers.outerpark.domain.seat.entity.Seat;
import com.lockers.outerpark.domain.seat.repository.ReservationSeatRepository;
import com.lockers.outerpark.domain.seat.repository.SeatRepository;
import com.lockers.outerpark.domain.user.entity.User;
import com.lockers.outerpark.domain.user.entity.UserRole;
import com.lockers.outerpark.domain.user.repository.UserRepository;

@ActiveProfiles("test")
@SpringBootTest
class ReservationServiceImplTest {

    private static final Logger log = LoggerFactory.getLogger(ReservationServiceImplTest.class);
    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationSeatRepository reservationSeatRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConcertRepository concertRepository;

    @BeforeEach
    void setUp() {

        reservationSeatRepository.deleteAll();
        reservationRepository.deleteAll();
        concertRepository.deleteAll();
        userRepository.deleteAll();

        if (seatRepository.count() == 0) {
            for (int i = 1; i <= 100; i++) {
                seatRepository.save(new Seat("A-" + i));
            }
        }
        for (int i = 0; i < 10; i++) {
            User user = new User("test" + i + "@naver.com", "김용준" + i,
                LocalDate.parse("2001-01-29"), "213$@ds@D", 100000L, UserRole.USER);
            userRepository.save(user);
        }
        User writer = new User("test10@naver.com", "김용준10", LocalDate.parse("2001-01-29"), "213$@ds@D", 100000L,
            UserRole.ADMIN);

        // userRepository.save(user);
        userRepository.save(writer);

        RegisterConcertRequest registerConcertRequest = new RegisterConcertRequest();
        ReflectionTestUtils.setField(registerConcertRequest, "title", "제목");
        ReflectionTestUtils.setField(registerConcertRequest, "runningTime", 180);
        ReflectionTestUtils.setField(registerConcertRequest, "price", 65000);
        ReflectionTestUtils.setField(registerConcertRequest, "limitAge", 19);
        ReflectionTestUtils.setField(registerConcertRequest, "performanceDate", LocalDate.parse("2025-07-11"));
        Concert concert = Concert.of(writer, registerConcertRequest);

        concertRepository.save(concert);
    }

    @Test
    void concurrentReservationTest() throws InterruptedException {
        //given
        Long userId = 1L;
        Long concertId = 1L;
        List<Long> seatIds = List.of(1L, 2L); // 동일 좌석 충돌 유도
        List<User> all = userRepository.findAll();
        int THREAD_COUNT = 10;

        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        CyclicBarrier barrier = new CyclicBarrier(THREAD_COUNT);
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        List<Throwable> exceptions = Collections.synchronizedList(new ArrayList<>());

        for (int i = 0; i < THREAD_COUNT; i++) {
            Long id = all.get(i).getId();
            executorService.execute(() -> {
                ReservationRequest req = new ReservationRequest();
                ReflectionTestUtils.setField(req, "seatIds", seatIds);
                try {
                    barrier.await();
                    reservationService.createReservation(req, id, concertId);
                } catch (Exception e) {
                    exceptions.add(e);
                    log.warn("예약 실패: userId={}, reason={}", userId, e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        List<Reservation> reservations = reservationRepository.findByConcertId(concertId);
        System.out.println("Reservation Count: " + reservations.size());
        reservations.forEach(System.out::println);

        // assert only one reservation succeeded
        assertThat(reservations.size()).isEqualTo(1);
        // assertThat(exceptions.size()).isEqualTo(1); // 또는 예외 타입 체크

    }
}