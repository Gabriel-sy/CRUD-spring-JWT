create table note (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    content TEXT NOT NULL,
    category TEXT UNIQUE
);