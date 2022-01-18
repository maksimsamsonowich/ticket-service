CREATE TABLE locations (
    id bigserial not null,
    title varchar(50) not null,
    address varchar(255) not null,
    capacity integer not null,
    primary key ("id")
);