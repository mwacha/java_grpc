CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE public.category (
	id uuid NOT NULL PRIMARY KEY,
	"name" varchar NOT NULL,
	description varchar NULL
);

CREATE TABLE public.course (
	id uuid NOT NULL PRIMARY KEY,
	"name" varchar NOT NULL,
	description varchar NULL,
	category_id uuid NOT NULL,
    CONSTRAINT course_category_fkey FOREIGN KEY (category_id) REFERENCES public.category (id)
);
