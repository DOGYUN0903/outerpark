package com.lockers.outerpark.domain.payment.exception;

import com.lockers.outerpark.common.exception.BusinessException;

public class PaymentException extends BusinessException {

	public PaymentException(PaymentErrorCode errorCode) {
		super(errorCode);
	}
}
