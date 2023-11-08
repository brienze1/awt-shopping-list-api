SET SCHEMA shopping_list;

CREATE TABLE shopping_list
(
    id       uuid        NOT NULL DEFAULT uuid_generate_v4(),
    user_id  uuid        NOT NULL,
    name     varchar(50) NOT NULL,
    CONSTRAINT shopping_list_pk PRIMARY KEY (id),
    CONSTRAINT fk_shopping_list_user FOREIGN KEY (user_id) REFERENCES users (id)
);