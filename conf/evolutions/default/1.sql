# --- !Ups

CREATE TABLE movies (
    id uuid NOT NULL,
    name CHARACTER VARYING (50) NOT NULL,
    ranking INTEGER NOT NULL,
    release_date TIMESTAMP WITH TIME ZONE NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE shows (
    id uuid NOT NULL,
    name CHARACTER VARYING (50) NOT NULL,
    ranking INTEGER NOT NULL,
    release_date TIMESTAMP WITH TIME ZONE NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE seasons (
    id uuid NOT NULL,
    release_date TIMESTAMP WITH TIME ZONE NOT NULL,
    show_id uuid NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (show_id) REFERENCES shows(id)
);

CREATE TABLE episodes (
    id uuid NOT NULL,
    name CHARACTER VARYING (50) NOT NULL,
    description VARCHAR NOT NULL,
    release_date TIMESTAMP WITH TIME ZONE NOT NULL,
    season_id uuid NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (season_id) REFERENCES seasons(id)
);


# --- !Downs

DROP TABLE episodes;
DROP TABLE shows;
DROP TABLE seasons;
DROP TABLE episodes;
