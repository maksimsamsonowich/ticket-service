create table user_roles (
    cred_id integer not null,
    role_id integer not null,
    foreign key (cred_id) references creds(id),
    foreign key (role_id) references roles(id)
);

insert into user_roles values (1, 2)