package com.lockers.outerpark.domain.payment.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import com.lockers.outerpark.domain.payment.dto.request.PaymentCreateRequest;
import com.lockers.outerpark.domain.payment.dto.response.PaymentCreateResponse;
import com.lockers.outerpark.domain.payment.dto.response.PaymentResponse;
import com.lockers.outerpark.domain.payment.entity.Payment;
import com.lockers.outerpark.domain.payment.exception.PaymentErrorCode;
import com.lockers.outerpark.domain.payment.exception.PaymentException;
import com.lockers.outerpark.domain.payment.repository.PaymentRepository;
import com.lockers.outerpark.domain.payment.type.PaymentStatus;
import com.lockers.outerpark.domain.reservation.entity.Reservation;
import com.lockers.outerpark.domain.reservation.service.ReservationService;
import com.lockers.outerpark.domain.user.entity.User;
import com.lockers.outerpark.domain.user.service.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

	@InjectMocks
	private PaymentServiceImpl paymentService;

	@Mock
	private PaymentRepository paymentRepository;

	@Mock
	private ReservationService reservationService;

	@Mock
	private UserServiceImpl userService;

	@Test
	void 결제가_성공적으로_저장된다() {
		// given
		Long userId = 42L;
		Long concertId = 1L;
		int amount = 100000;

		PaymentCreateRequest request = new PaymentCreateRequest("CARD", PaymentStatus.SUCCESS);

		Reservation reservation = mock(Reservation.class);
		when(reservation.getAmount()).thenReturn(amount);
		when(reservationService.getReservationByUserIdAndConcertId(userId, concertId)).thenReturn(reservation);

		User user = mock(User.class);
		when(user.getBalance()).thenReturn(200000L);
		when(userService.getActiveUserById(userId)).thenReturn(user);

		Payment payment = Payment.builder()
			.id(10L)
			.reservation(reservation)
			.totalAmount(amount)
			.method("CARD")
			.status(PaymentStatus.SUCCESS)
			.build();

		when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

		// when
		PaymentCreateResponse response = paymentService.createPayment(request, concertId, userId);

		// then
		assertNotNull(response);
		assertEquals(10L, response.id());
		verify(paymentRepository).save(any(Payment.class));
	}

	@Test
	void 데이터_무결성_예외_발생시_예약취소_그리고_예외() {
		// given
		Long userId = 42L;
		Long concertId = 1L;
		int amount = 100000;

		PaymentCreateRequest request = new PaymentCreateRequest("CARD", PaymentStatus.SUCCESS);

		Reservation reservation = mock(Reservation.class);
		when(reservation.getAmount()).thenReturn(amount);
		when(reservationService.getReservationByUserIdAndConcertId(userId, concertId)).thenReturn(reservation);

		User user = mock(User.class);
		when(user.getBalance()).thenReturn(200000L);
		when(userService.getActiveUserById(userId)).thenReturn(user);

		when(paymentRepository.save(any(Payment.class)))
			.thenThrow(new DataIntegrityViolationException("예외 발생"));

		// when & then
		PaymentException ex = assertThrows(PaymentException.class,
			() -> paymentService.createPayment(request, concertId, userId)
		);

		// 예외 코드 확인
		assertEquals(PaymentErrorCode.INVALID_PAYMENT_REQUEST, ex.getErrorCode());

		// 예약 취소가 호출되었는지 확인
		verify(reservationService).updateReservationCancel(reservation.getId());
	}

	@Test
	void 예상치_못한_예외_발생시_예약취소_그리고_예외() {
		// given
		Long userId = 42L;
		Long concertId = 1L;
		int amount = 100000;

		PaymentCreateRequest request = new PaymentCreateRequest("CARD", PaymentStatus.SUCCESS);

		Reservation reservation = mock(Reservation.class);
		when(reservation.getAmount()).thenReturn(amount);
		when(reservationService.getReservationByUserIdAndConcertId(userId, concertId)).thenReturn(reservation);

		User user = mock(User.class);
		when(user.getBalance()).thenReturn(200000L);
		when(userService.getActiveUserById(userId)).thenReturn(user);

		Payment payment = new Payment();

		when(paymentRepository.save(payment)).thenThrow(RuntimeException.class);

		// when & then
		PaymentException ex = assertThrows(PaymentException.class,
			() -> paymentService.createPayment(request, concertId, userId)
		);

		// 예외 코드 확인
		assertEquals(PaymentErrorCode.PAYMENT_FAILED, ex.getErrorCode());

		// 예약 취소가 호출되었는지 확인
		verify(reservationService).updateReservationCancel(reservation.getId());
	}

	@Test
	void 결제_성공이_아닐_경우_예외_발생() {
		// given
		Long concertId = 1L;
		Long userId = 33L;
		PaymentCreateRequest request = mock(PaymentCreateRequest.class);
		when(request.getStatus()).thenReturn(PaymentStatus.CANCEL);

		Reservation reservation = mock(Reservation.class);
		when(reservation.getId()).thenReturn(1L);
		when(reservationService.getReservationByUserIdAndConcertId(userId, concertId)).thenReturn(reservation);

		//when
		PaymentException ex = assertThrows(PaymentException.class, () ->
			paymentService.createPayment(request, concertId, userId)
		);

		//then
		assertEquals(PaymentErrorCode.PAYMENT_FAILED, ex.getErrorCode());
	}

	@Test
	void 결제_금액이_부족할_경우_예외_발생() {
		// given
		Long concertId = 1L;
		Long userId = 33L;
		PaymentCreateRequest request = new PaymentCreateRequest("POINT", PaymentStatus.SUCCESS);

		Reservation reservation = mock(Reservation.class);
		when(reservation.getAmount()).thenReturn(90000);
		when(reservationService.getReservationByUserIdAndConcertId(userId, concertId)).thenReturn(reservation);

		User user = mock(User.class);
		when(user.getBalance()).thenReturn(50000L);
		when(userService.getActiveUserById(userId)).thenReturn(user);

		//when
		PaymentException ex = assertThrows(PaymentException.class, () ->
			paymentService.createPayment(request, concertId, userId)
		);

		//then
		assertEquals(PaymentErrorCode.PAYMENT_FAILED, ex.getErrorCode());
	}

	@Test
	void 결제_조회시_결제_ID가_일치하지_않으면_예외_발생() {
		//given
		Long paymentId = 1L;

		Payment payment = Payment.builder()
			.id(10L)
			.method("CARD")
			.status(PaymentStatus.SUCCESS)
			.build();

		// when
		PaymentException ex = assertThrows(PaymentException.class, () ->
			paymentService.getPayment(paymentId)
		);

		assertEquals(PaymentErrorCode.NOT_FOUND_PAYMENT, ex.getErrorCode());

	}

	@Test
	void 결제_조회시_정상_반환() {
		// given
		Long paymentId = 1L;
		Long reservationId = 10L;

		Reservation reservation = mock(Reservation.class);
		when(reservation.getId()).thenReturn(reservationId);

		Payment payment = Payment.builder()
			.id(paymentId)
			.reservation(reservation)
			.build();

		Mockito.when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(payment));

		// when
		PaymentResponse response = paymentService.getPayment(paymentId);

		// then
		assertNotNull(response);
		assertEquals(paymentId, response.id());
		assertEquals(reservationId, response.reservationId());

	}

	@Test
	void 결제_취소시_결제정보와_유저정보가_정상적으로_업데이트() {
		// given
		Long paymentId = 1L;
		Long userId = 42L;
		int totalAmount = 10000;

		// Reservation mock
		Reservation reservation = mock(Reservation.class);
		when(reservation.getId()).thenReturn(1L);

		// Payment mock
		Payment payment = Payment.builder()
			.id(paymentId)
			.reservation(reservation)
			.status(PaymentStatus.SUCCESS)
			.totalAmount(totalAmount)
			.build();

		// User mock
		User user = mock(User.class);
		when(user.getBalance()).thenReturn(30000L);

		// mocking
		when(paymentRepository.findByIdAndStatus(paymentId, PaymentStatus.SUCCESS)).thenReturn(Optional.of(payment));
		when(paymentRepository.countCancelable(paymentId, LocalDate.now().plusDays(1))).thenReturn(1L);
		when(userService.getActiveUserById(userId)).thenReturn(user);
		doNothing().when(reservationService).updateReservationCancel(anyLong());

		// when
		paymentService.updatePaymentCancel(paymentId, userId);

		// then
		assertEquals(PaymentStatus.CANCEL, payment.getStatus());
		verify(user).updateBalance(40000L);
	}

	@Test
	void 결제_조회시_결제_정보가_없으면_예외_발생() {
		//given
		Long paymentId = 1L;
		Long userId = 3L;

		// when
		PaymentException ex = assertThrows(PaymentException.class, () ->
			paymentService.updatePaymentCancel(paymentId, userId)
		);

		assertEquals(PaymentErrorCode.NOT_FOUND_PAYMENT, ex.getErrorCode());

	}

	@Test
	void 공연날짜가_지난_경우_결제취소시_예외_발생() {
		// given
		Long paymentId = 1L;
		Long userId = 33L;

		Payment mockPayment = Payment.builder()
			.id(paymentId)
			.totalAmount(10000)
			.status(PaymentStatus.SUCCESS)
			.reservation(new Reservation())
			.build();

		// 결제 상태 SUCCESS인 Payment 찾기 → 성공
		Mockito.when(paymentRepository.findByIdAndStatus(paymentId, PaymentStatus.SUCCESS))
			.thenReturn(Optional.of(mockPayment));

		// 공연 날짜가 이미 지남
		Mockito.when(paymentRepository.countCancelable(paymentId, LocalDate.now().plusDays(1)))
			.thenReturn(0L);

		// when
		PaymentException ex = assertThrows(PaymentException.class, () ->
			paymentService.updatePaymentCancel(paymentId, userId)
		);

		//then
		assertEquals(PaymentErrorCode.CANCELLATION_PERIOD_EXPIRED, ex.getErrorCode());
	}
}

