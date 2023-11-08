SET SCHEMA shopping_list;

CREATE TABLE user_authorization
(
    id                 uuid   NOT NULL DEFAULT uuid_generate_v4(),
    user_credential_id uuid   NOT NULL,
    token              text   NOT NULL,
    issued_at          date NOT NULL,
    expires_at         date NOT NULL,
    CONSTRAINT user_authorization_pk PRIMARY KEY (id),
    CONSTRAINT fk_user_authorization_user_credential FOREIGN KEY (user_credential_id) REFERENCES user_credential (id)
);