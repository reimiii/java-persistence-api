create database jpa;

use jpa;

show tables;

create table customers
(
    id   varchar(225) not null primary key,
    name varchar(100) not null
) engine InnoDB;

alter table customers
    add column primary_email varchar(150);

select *
from customers;