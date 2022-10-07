CREATE TABLE IF NOT EXISTS account
(
    id           UUID NOT NULL PRIMARY KEY,
    username     VARCHAR(30) NOT NULL UNIQUE,
    password     VARCHAR(30) NOT NULL
);
