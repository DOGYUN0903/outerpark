package com.lockers.outerpark.domain.reservation.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lockers.outerpark.domain.concert.entity.Concert;
import com.lockers.outerpark.domain.concert.service.ConcertService;
import com.lockers.outerpark.domain.reservation.dto.request.ReservationRequest;
import com.lockers.outerpark.domain.reservation.dto.response.ReservationResponse;
import com.lockers.outerpark.domain.reservation.dto.response.UserReservationResponse;
import com.lockers.outerpark.domain.reservation.entity.Reservation;
import com.lockers.outerpark.domain.reservation.entity.ReservationStatus;
import com.lockers.outerpark.domain.reservation.exception.ReservationErrorCode;
import com.lockers.outerpark.domain.reservation.exception.ReservationException;
import com.lockers.outerpark.domain.reservation.repository.ReservationRepository;
import com.lockers.outerpark.domain.seat.entity.ReservationSeat;
import com.lockers.outerpark.domain.seat.entity.Seat;
import com.lockers.outerpark.domain.seat.service.SeatService;
import com.lockers.outerpark.domain.user.entity.User;
import com.lockers.outerpark.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationServiceImpl implements ReservationService {

    private final UserService userService;
    private final ConcertService concertService;
    private final SeatService seatService;
    private final ReservationRepository reservationRepository;

    @Override
    @Transactional
    public ReservationResponse createReservationV1(ReservationRequest request, Long userId, Long concertId) {
        List<Long> seatIds = request.getSeatIds();

        log.info("✅ 예약 시도: userId={}, concertId={}, seatIds={}", userId, concertId, seatIds);
        //같은 공연에 이미 PENDING 중인 예약이 있을경우 예외
        if (reservationRepository.existsByUserIdAndConcertIdAndStatus(userId, concertId,
            ReservationStatus.PENDING)) {
            throw new ReservationException(ReservationErrorCode.ALREADY_PENDING);
        }

        List<Seat> seats = seatService.getSeatsForReservation(request.getSeatIds(), concertId);
        User user = userService.getActiveUserById(userId);
        Concert concert = concertService.getActiveConcert(concertId);

        Reservation reservation = new Reservation(user, concert, seats.size(),
            concert.getPrice() * seats.size());

        Reservation savedReservation = reservationRepository.save(
            reservationAddReservationSeats(seats, reservation, concert));

        return ReservationResponse.fromEntity(savedReservation);
    }

    /*
     * DB 락 적용을 위한 분기 서비스 로직
     */
    @Override
    @Transactional
    public ReservationResponse createReservationV2(ReservationRequest request, Long userId, Long concertId) {
        log.info("✅ 예약 시도: userId={}, concertId={}, seatIds={}", userId, concertId, request.getSeatIds());

        List<Seat> seats = seatService.getSeatsForReservation(request.getSeatIds(), concertId);

        //같은 공연에 이미 PENDING 중인 예약이 있을경우 예외
        if (reservationRepository.existsByUserIdAndConcertIdAndStatus(userId, concertId,
            ReservationStatus.PENDING)) {
            throw new ReservationException(ReservationErrorCode.ALREADY_PENDING);
        }

        User user = userService.getActiveUserById(userId);
        Concert concert = concertService.getActiveConcert(concertId);

        Reservation reservation = new Reservation(user, concert, seats.size(),
            concert.getPrice() * seats.size());

        Reservation savedReservation = reservationRepository.save(
            reservationAddReservationSeats(seats, reservation, concert));

        return ReservationResponse.fromEntity(savedReservation);
    }

    @Override
    @Transactional
    public void cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findByIdAndStatusNot(reservationId, ReservationStatus.CANCELLED)
            .orElseThrow(() -> new ReservationException(ReservationErrorCode.NOT_FOUND));

        reservation.cancel();
    }

    @Override
    @Transactional
    public void confirmReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findByIdAndStatus(reservationId, ReservationStatus.PENDING)
            .orElseThrow(() -> new ReservationException(ReservationErrorCode.NOT_FOUND));

        reservation.confirm();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserReservationResponse> getUserReservations(Long userId, Pageable pageable) {
        return reservationRepository.findAllByUserIdAndStatus(userId,
            ReservationStatus.CONFIRMED, pageable).map(UserReservationResponse::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsReservation(Long reservationId) {
        if (reservationId == null)
            return false;
        return reservationRepository.existsById(reservationId);
    }

    @Override
    @Transactional(readOnly = true)
    public Reservation findReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId)
            .orElseThrow(() -> new ReservationException(ReservationErrorCode.NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public Reservation findReservationByUserIdAndConsortId(Long userId, Long concertId) {
        List<Reservation> reservationList = reservationRepository
            .findByUserIdAndConcertIdAndStatus(userId, concertId, ReservationStatus.PENDING);

        //하나의 공연에 PENDING 상태가 2개 이상이거나 없을 경우 예외처리
        if (reservationList.size() != 1) {
            throw new ReservationException(ReservationErrorCode.INVALID_RESERVATION_STATE);
        }
        return reservationList.get(0);
    }

    /**
     * private 메서드
     */
    private Reservation reservationAddReservationSeats(List<Seat> seats, Reservation reservation, Concert concert) {
        for (Seat seat : seats) {
            String reservationNumber = createReservationNumber(concert, seat.getSeatNumber());
            ReservationSeat reservationSeat = new ReservationSeat(seat, reservationNumber);

            reservation.addReservationSeat(reservationSeat);
        }

        return reservation;
    }

    private String createReservationNumber(Concert concert, String seatNumber) {
        return "T" + String.format("%04d", concert.getId()) + String.format("%02d",
            concert.getPerformanceDate().getDayOfMonth()) + seatNumber;
    }
}
