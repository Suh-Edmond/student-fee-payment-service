package com.student_mgt_platform.fee_payment.domain.exceptions;

public class BusinessValidationException extends RuntimeException{
    public BusinessValidationException(String message) {
        super(message);
    }
}
