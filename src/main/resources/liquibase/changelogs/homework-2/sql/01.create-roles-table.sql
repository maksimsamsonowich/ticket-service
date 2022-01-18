CREATE TABLE roles (
    id bigserial unique,
    role_name varchar(20) NOT NULL unique,
    PRIMARY KEY (id)
);