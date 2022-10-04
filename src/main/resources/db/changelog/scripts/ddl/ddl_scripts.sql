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