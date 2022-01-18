CREATE TABLE users (
    id bigserial unique,
    creds_id integer,
    phone_number varchar(15) null,
    firstname varchar null,
    surname varchar null,
    PRIMARY KEY (id),
    foreign key (creds_id) references creds(id)
);