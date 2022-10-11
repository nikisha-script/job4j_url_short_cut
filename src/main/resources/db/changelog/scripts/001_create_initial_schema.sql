--liquibase formatted sql

--changeset nikishin:create_person
create table if not exists persons(
    id serial primary key,
    login varchar unique,
    password varchar
);

--changeset nikishin:create_table_url
create table if not exists sites (
    id serial primary key,
    name text,
    login text,
    count int default 0,
    person_id int references persons(id)
);





