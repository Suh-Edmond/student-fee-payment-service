package com.student_mgt_platform.fee_payment.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
public class StudentAccount extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "student_number")
    private String studentNumber;

    @Column(name = "current_balance")
    private BigDecimal currentBalance;

    @Column(name = "next_due_date")
    private LocalDate nextDueDate;

    @OneToMany(mappedBy = "studentAccount")
    private Set<FeePayment> feePayments;
}
