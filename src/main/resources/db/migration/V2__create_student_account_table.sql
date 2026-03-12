create table student_accounts
(
    id              binary(16) not null,
    current_balance decimal(38, 2),
    next_due_date   date,
    student_number  varchar(255),
    primary key (id)
);