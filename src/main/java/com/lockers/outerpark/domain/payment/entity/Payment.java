package com.lockers.outerpark.domain.payment.entity;

import com.lockers.outerpark.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

	//todo: Reservation 외래키 적용

	@Column(nullable = false)
	private int totalAmount;

	@Column(nullable = false)
	private int count;

	@Column(nullable = false)
	private String method;

	@Column(nullable = false)
	private String status;
}
