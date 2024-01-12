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


create table members
(
    id          int          not null auto_increment primary key,
    email       varchar(150) not null,
    title       varchar(100),
    first_name  varchar(100) not null,
    middle_name varchar(100),
    last_name   varchar(100)
) engine innodb;

select *
from members;

create table departments
(
    company_id    varchar(100) not null,
    department_id varchar(100) not null,
    name          varchar(150) not null,
    primary key (company_id, department_id)
) engine innodb;

select *
from departments;

create table hobbies
(
    id        int          not null auto_increment primary key,
    member_id int          not null,
    name      varchar(100) not null,
    foreign key fk_members_hobbies (member_id)
        references members (id)
) engine innodb;

select *
from hobbies;