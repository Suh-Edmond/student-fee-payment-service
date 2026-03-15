create table student_accounts
(
    id                   binary(16) not null,
    next_due_date        date,
    created              datetime(6),
    updated              datetime(6),
    institutional_fee_id binary(16),
    student_name         varchar(255),
    student_number       varchar(255),
    primary key (id)
);
alter table student_accounts
    add constraint UKijxidh1mwcfcmk9lfirjsbuyq unique (institutional_fee_id);
alter table student_accounts
    add constraint FKbmhebg1ten7t46enllq93fqkt foreign key (institutional_fee_id) references institutional_fees (id)