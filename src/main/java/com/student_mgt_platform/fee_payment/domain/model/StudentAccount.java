package com.student_mgt_platform.fee_payment.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Table(name = "student_accounts")
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class StudentAccount extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "student_number")
    private String studentNumber;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "next_due_date")
    private LocalDate nextDueDate;

    @OneToOne()
    private InstitutionalFee institutionalFee;

    @OneToMany(mappedBy = "studentAccount")
    private Set<FeePayment> feePayments;
}
