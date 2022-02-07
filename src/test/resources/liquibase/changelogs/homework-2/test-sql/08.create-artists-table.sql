CREATE TABLE artists (
    users_id integer NOT NULL,
    nickname varchar(40) NOT NULL,
    id serial,
    PRIMARY KEY (id),
    foreign key (users_id) references users(id)
);