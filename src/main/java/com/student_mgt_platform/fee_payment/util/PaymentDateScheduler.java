package com.student_mgt_platform.fee_payment.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class PaymentDateScheduler {
    private static int PAYMENT_DUE_DAYS = 90;
    private static List<DayOfWeek> WEEK_DAYS = Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY);

    public static LocalDate computeNextPaymentDueDate(final LocalDate currentPaymentDate) {
        LocalDate nextPayDate = currentPaymentDate.plusDays(PAYMENT_DUE_DAYS);
        if (WEEK_DAYS.contains(currentPaymentDate.getDayOfWeek())) {
            return nextPayDate;
        }
        else if (currentPaymentDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
            nextPayDate = nextPayDate.plusDays(2);
        }else {
            nextPayDate = nextPayDate.plusDays(1);
        }

        return nextPayDate;
    }
}
