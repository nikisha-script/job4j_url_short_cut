--liquibase formatted sql

--changeset nikishin:create_table_url
create table if not exists url (
    id serial primary key,
    name text,
    login text,
    password text

);

--changeset nikishin:alter_url_add_unique_field
alter table url add constraint full_unique_url unique (name, login, password);

--changeset nikishin:create_person
create table if not exists persons(
    id serial primary key,
    login varchar unique,
    password varchar
);

--changeset nikishin:alter_url_add_person
alter table url add column person_id int references persons(id);

--changeset nikishin:alter_url_add_count
alter table url add column count int;

