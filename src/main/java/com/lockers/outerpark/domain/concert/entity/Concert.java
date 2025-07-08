package com.lockers.outerpark.domain.concert.entity;

import java.time.LocalDateTime;

import com.lockers.outerpark.common.entity.BaseEntity;
import com.lockers.outerpark.domain.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "concerts")
@Getter
public class Concert extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "writer_id")
	private User writer;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private int runningTime;

	@Column(nullable = false)
	private int price;

	@Column(nullable = false)
	private int limitAge;

	@Column(nullable = false)
	private LocalDateTime performanceDate;
}
