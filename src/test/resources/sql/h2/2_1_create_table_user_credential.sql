SET SCHEMA shopping_list;

CREATE TABLE user_credential
(
    id       uuid        NOT NULL DEFAULT uuid_generate_v4(),
    user_id  uuid        NOT NULL,
    username varchar(20) NOT NULL UNIQUE,
    password varchar     NOT NULL,
    secret   uuid        NOT NULL,
    CONSTRAINT user_credential_pk PRIMARY KEY (id),
    CONSTRAINT fk_user_credential_user FOREIGN KEY (user_id) REFERENCES users (id)
);