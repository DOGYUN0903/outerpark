package com.lockers.outerpark.domain.payment.repository;

import java.time.LocalDate;
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
	 * @return 취소 가능 조건을 만족하는 개수 (0이면 불가능, 1 이상이면 가능)
	 */
	@Query("""
		SELECT COUNT(p)
		FROM Payment p
		JOIN p.reservation r
		JOIN r.concert c
		WHERE p.id = :paymentId
		AND c.performanceDate >= :tomorrow
		""")
	Long countCancelable(@Param("paymentId") Long paymentId, @Param("tomorrow") LocalDate tomorrow);
}
