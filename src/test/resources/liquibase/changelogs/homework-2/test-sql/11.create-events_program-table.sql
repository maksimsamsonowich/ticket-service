CREATE TABLE events_program (
    continuance TIME NOT NULL,
    event_name VARCHAR(255) NOT NULL,
    id serial,
    price integer NOT NULL,
    PRIMARY KEY ("id")
);