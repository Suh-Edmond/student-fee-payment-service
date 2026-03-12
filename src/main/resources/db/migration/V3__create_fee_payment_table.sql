create table fee_payment (
     id binary(16) not null,
    incentive_amount decimal(38,2),
    incentive_rate integer,
    new_balance decimal(38,2),
    payment_amount decimal(38,2),
    payment_date date,
    previous_balance decimal(38,2),
    student_account_id binary(16),
    primary key (id)
);
create index index_payment_date on fee_payment (payment_date);
alter table fee_payment add constraint FKl8wjdprfgi5bw91aqfhf3tcf1 foreign key (student_account_id) references student_accounts (id)
