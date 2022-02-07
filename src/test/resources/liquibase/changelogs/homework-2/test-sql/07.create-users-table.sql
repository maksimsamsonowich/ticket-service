CREATE TABLE users (
    id serial,
    creds_id integer,
    phone_number varchar(15),
    firstname varchar,
    surname varchar,
    PRIMARY KEY (id),
    foreign key (creds_id) references creds(id)
);