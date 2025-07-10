package com.lockers.outerpark.domain.payment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lockers.outerpark.domain.payment.entity.Payment;
import com.lockers.outerpark.domain.payment.type.PaymentStatus;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
	/**
	 * 지정된 ID와 상태를 가진 결제 정보를 조회합니다.
	 *
	 * @param id 결제 ID
	 * @param status 결제 상태 (예: SUCCESS, CANCEL)
	 * @return 일치하는 결제가 존재하면 해당 결제, 없으면 Optional.empty()
	 */
	Optional<Payment> findByIdAndStatus(Long id, PaymentStatus status);

	/**
	 * 결제 ID를 기반으로 해당 결제가 아직 취소 가능한 상태인지 여부를 확인합니다.
	 * 공연일의 하루 전까지는 취소 가능하다고 판단합니다.
	 *
	 * @param paymentId 결제 ID
	 * @param now 현재 시간 기준 (LocalDateTime.now()를 호출한 값)
	 * @return true: 공연일 기준 하루 전까지이면 취소 가능, false: 공연 당일이거나 지났으면 취소 불가
	 */
	@Query(value = """
		SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END
		FROM payments p
		JOIN reservations r ON p.reservation_id = r.id
		JOIN concerts c ON r.concert_id = c.id
		WHERE p.id = :paymentId
		AND c.performance_date >= DATE_ADD(NOW(), INTERVAL 1 DAY)
		""", nativeQuery = true)
	boolean isCancelable(@Param("paymentId") Long paymentId);
}
