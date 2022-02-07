CREATE TABLE events (
    id serial,
    title varchar(255) not null,
    artists_id integer DEFAULT NULL,
    locations_id integer DEFAULT NULL,
    description varchar(255) NOT NULL,
    occupied_places integer NOT NULL,
    age_limit integer NOT NULL,
    date date NOT NULL,
    events_program_id integer DEFAULT NULL,
    PRIMARY KEY (id),
    foreign key (artists_id) references artists(id),
    foreign key (locations_id) references locations(id),
    foreign key (events_program_id) references events_program(id)
);