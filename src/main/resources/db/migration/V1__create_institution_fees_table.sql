create table institutional_fees
(
    id             binary(16) not null,
    amount_payable decimal(38, 2),
    created        datetime(6),
    updated        datetime(6),
    name           varchar(255),
    category       enum ('FRESH_MEN','SENIOR','SOPHOMORE'),
    primary key (id)
);
