package com.lockers.outerpark.domain.payment.entity;

import com.lockers.outerpark.common.entity.BaseEntity;
import com.lockers.outerpark.domain.reservation.entity.Reservation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "payments")
public class Payment extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reservation_id", nullable = false)
	private Reservation reservation;

	@Column(nullable = false)
	private int totalAmount;

	@Column(nullable = false)
	private String method;

	@Column(nullable = false)
	private String status;

	@Builder
	public Payment(Long id, Reservation reservation, int totalAmount, String method, String status) {
		this.id = id;
		this.reservation = reservation;
		this.totalAmount = totalAmount;
		this.method = method;
		this.status = status;
	}

	public void updateStatus(String status) {
		this.status = status;
	}
}
