create table fee_payments
(
    incentive_amount   decimal(38, 2),
    incentive_rate     integer,
    new_balance        decimal(38, 2),
    payment_amount     decimal(38, 2),
    payment_date       date,
    previous_balance   decimal(38, 2),
    created            datetime(6),
    updated            datetime(6),
    id                 binary(16) not null,
    student_account_id binary(16),
    primary key (id)
);
create index index_payment_date on fee_payments (payment_date);
alter table fee_payments
    add constraint FKei12bij898wd3mff6j4cq7ddn foreign key (student_account_id) references student_accounts (id)