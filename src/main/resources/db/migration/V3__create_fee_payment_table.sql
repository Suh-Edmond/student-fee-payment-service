create table fee_payments
(
    id                 binary(16) not null,
    incentive_amount   decimal(38, 2),
    incentive_rate     integer,
    new_balance        decimal(38, 2),
    payment_amount     decimal(38, 2),
    payment_date       date,
    previous_balance   decimal(38, 2),
    created            datetime(6),
    updated            datetime(6),
    student_account_id binary(16),
    primary key (id)
);
create index index_payment_date on fee_payments(payment_date);
alter table fee_payments
    add constraint FKl8wjdprfgi5bw91aqfhf3tcf1 foreign key (student_account_id) references student_accounts (id)