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

alter table customers
    add column age     tinyint,
    add column married boolean;

alter table customers
    add column type varchar(50);

select *
from customers;

create table categories
(
    id          int          not null auto_increment primary key,
    name        varchar(100) not null,
    description varchar(500)
) engine InnoDB;

alter table categories
    add column created_at timestamp,
    add column updated_at timestamp;

select *
from categories;


create table images
(
    id          int          not null auto_increment primary key,
    name        varchar(100) not null,
    description text,
    image       blob
) engine InnoDB;

select *
from images;