package com.lockers.outerpark.domain.seat.entity;


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
@Table(name = "seats")
@Getter
@NoArgsConstructor
public class Seat extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String seatNumber;

	public Seat(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	/**
	 * 좌석 번호 표시용 문자열 반환
	 */
	public String getDisplaySeatNumber() {
		return this.seatNumber;
	}
}
