package com.lockers.outerpark.domain.payment.type;

public enum PaymentStatus {
	//상태는 추후 더 추가 예정
	CANCEL("CANCEL"),
	SUCCESS("SUCCESS");

	private final String status;

	PaymentStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}
