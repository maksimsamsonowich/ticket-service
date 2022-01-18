CREATE TABLE tickets (
    id bigserial not null,
    user_id integer NOT NULL,
    event_id integer NOT NULL,
    order_date date NOT NULL,
    foreign key (user_id) references users(id),
    foreign key (event_id) references events(id)
);