create database jpa;

use jpa;

show tables;

create table customers
(
    id   varchar(225) not null primary key,
    name varchar(100) not null
) engine InnoDB;

select *
from customers;