package com.student_mgt_platform.fee_payment.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class PaymentDateScheduler {
    private static final int PAYMENT_DUE_DAYS = 90;

    public static LocalDate computeNextPaymentDueDate(final LocalDate currentPaymentDate) {
        LocalDate nextPayDate = currentPaymentDate.plusDays(PAYMENT_DUE_DAYS);
        DayOfWeek dayOfWeek = nextPayDate.getDayOfWeek();
        return switch (dayOfWeek) {
            case SATURDAY -> nextPayDate.plusDays(2);
            case SUNDAY ->  nextPayDate.plusDays(1);
            default -> nextPayDate;
        };
    }
}
