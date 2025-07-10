package com.lockers.outerpark.domain.payment.type;

public enum PaymentStatus {
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
