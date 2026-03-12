create table institutional_fee
(
    id             binary(16) not null,
    amount_payable decimal(38, 2),
    name           varchar(255),
    category       enum ('FRESH_MEN','SENIOR','SOPHOMORE'),
    primary key (id)
);           )