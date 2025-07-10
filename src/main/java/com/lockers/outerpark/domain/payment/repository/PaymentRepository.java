package com.lockers.outerpark.domain.payment.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lockers.outerpark.domain.payment.entity.Payment;
import com.lockers.outerpark.domain.payment.type.PaymentStatus;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
	Optional<Payment> findByIdAndStatus(Long id, PaymentStatus status);

	@Query("""
		SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END
		FROM Payment p
		JOIN p.reservation r
		JOIN r.concert c
		WHERE p.id = :paymentId
		AND c.performanceDate >= :now.plusDays(1)
		""")
	boolean isCancelable(Long paymentId, LocalDateTime now);
}
