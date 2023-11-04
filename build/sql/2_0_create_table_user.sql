SET SCHEMA 'shopping_list';

CREATE TABLE "user"
(
    id         uuid        NOT NULL DEFAULT uuid_generate_v4(),
    first_name varchar(50) NOT NULL,
    last_name  varchar(50) NOT NULL,
    CONSTRAINT user_pk PRIMARY KEY (id)
);