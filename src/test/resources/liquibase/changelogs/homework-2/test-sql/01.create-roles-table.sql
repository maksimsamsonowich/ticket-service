CREATE TABLE roles (
    id serial,
    role_name varchar(20) NOT NULL unique,
    PRIMARY KEY (id)
);