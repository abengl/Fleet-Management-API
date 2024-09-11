BEGIN;

-- taxis table
CREATE TABLE IF NOT EXISTS verceldb.public.taxis
(
    id integer NOT NULL,
    plate character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT taxis_pkey PRIMARY KEY (id)
);

-- trajectories table
CREATE TABLE IF NOT EXISTS verceldb.public.trajectories
(
    id serial NOT NULL,
    taxi_id integer,
    date timestamp(6) without time zone,
    latitude double precision,
    longitude double precision,
    CONSTRAINT trajectories_pkey PRIMARY KEY (id)
);

-- relationships
ALTER TABLE IF EXISTS verceldb.public.trajectories
    ADD CONSTRAINT trajectories_taxi_id_fkey FOREIGN KEY (taxi_id)
        REFERENCES public.taxis (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;

-- users table
CREATE TABLE IF NOT EXISTS verceldb.public.users
(
    id serial NOT NULL,
    name character varying COLLATE pg_catalog."default",
    email character varying COLLATE pg_catalog."default",
    password character varying COLLATE pg_catalog."default",
    CONSTRAINT users_pkey PRIMARY KEY (id)
);

-- Delete test user
DELETE FROM verceldb.public.users
WHERE email = 'newUser@test.com';

-- users table test values
INSERT INTO verceldb.public.users(name, email, password)
VALUES ('User 1', 'user1@example.com', 'password123'),
       ('User 2', 'user2@example.com', 'password123'),
       ('User 3', 'user3@example.com', 'password123'),
       ('User 4', 'user4@example.com', 'password123'),
       ('User 5', 'user5@example.com', 'password123'),
       ('User 6', 'user6@example.com', 'password123'),
       ('User 7', 'user7@example.com', 'password123'),
       ('User 8', 'user8@example.com', 'password123'),
       ('User 9', 'user9@example.com', 'password123');

END;