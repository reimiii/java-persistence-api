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

create table skills
(
    id        int          not null auto_increment primary key,
    member_id int          not null,
    name      varchar(100) not null,
    value     int          not null,
    foreign key fk_members_skills (member_id) references members (id),
    constraint skills_unique unique (member_id, name)
) engine innodb;

select *
from skills;

create table credentials
(
    id       varchar(100) not null primary key,
    email    varchar(150) not null,
    password varchar(150) not null
) engine innodb;

select *
from credentials;

create table users
(
    id   varchar(100) not null primary key,
    name varchar(150) not null
) engine innodb;

select *
from users;

create table wallet
(
    id      int          not null auto_increment primary key,
    user_id varchar(100) not null,
    balance bigint       not null,
    foreign key fk_users_wallet (user_id) references users (id)
) engine innodb;

select *
from wallet;

create table brands
(
    id          varchar(100) not null primary key,
    name        varchar(100) not null,
    description varchar(500)
) engine innodb;

select *
from brands;

create table products
(
    id          varchar(100) not null primary key,
    brand_id    varchar(100) not null,
    name        varchar(100) not null,
    price       bigint       not null,
    description varchar(500),
    foreign key fk_brands_products (brand_id) references brands (id)
) engine innodb;

select *
from products;


create table users_like_products
(
    user_id    varchar(100) not null,
    product_id varchar(100) not null,
    foreign key fk_users_users_like_products (user_id) references users (id),
    foreign key fk_products_users_like_products (product_id) references products (id),
    primary key (user_id, product_id)
) engine innodb;

select *
from users_like_products;

create table employees
(
    id             varchar(100) not null primary key,
    type           varchar(50)  not null,
    name           varchar(100) not null,
    total_manager  int,
    total_employee int
) engine innodb;

select *
from employees;

create table payments
(
    id     varchar(100) not null primary key,
    amount bigint       not null
) engine innodb;

select *
from payments;

create table payments_gopay
(
    id       varchar(100) not null primary key,
    gopay_id varchar(100) not null,
    foreign key fk_payments_gopay (id) references payments (id)
) engine innodb;

select *
from payments_gopay;

create table payments_credit_card
(
    id          varchar(100) not null primary key,
    masked_card varchar(100) not null,
    bank        varchar(100) not null,
    foreign key fk_payments_credit_card (id) references payments (id)
) engine innodb;

select *
from payments_credit_card;