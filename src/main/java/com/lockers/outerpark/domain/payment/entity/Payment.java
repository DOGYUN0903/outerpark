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
	private int count;

	@Column(nullable = false)
	private String method;

	@Column(nullable = false)
	private String status;

	public Payment(Reservation reservation, int totalAmount, int count, String method, String status) {
		this.reservation = reservation;
		this.totalAmount = totalAmount;
		this.count = count;
		this.method = method;
		this.status = status;
	}
}
