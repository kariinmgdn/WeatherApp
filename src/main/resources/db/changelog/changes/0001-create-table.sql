--liquibase formatted sql

--changeset karina:1

create table if not exists Weather (
    id serial primary key,
    ip varchar(255),
    time timestamp,
    latitude numeric,
    longitude numeric,
    temperature numeric,
    wind_speed numeric,
    wind_direction numeric,
    weather_code numeric
);