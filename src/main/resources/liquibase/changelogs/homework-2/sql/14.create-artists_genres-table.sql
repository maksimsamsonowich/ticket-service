CREATE TABLE artists_genres (
    artists_id integer NOT NULL,
    genres_id integer NOT NULL,
    primary key (artists_id, genres_id),
    foreign key (artists_id) references artists(id),
    foreign key (genres_id) references genres(id)
);
