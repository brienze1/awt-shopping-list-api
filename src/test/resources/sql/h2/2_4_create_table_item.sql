SET SCHEMA shopping_list;

CREATE TABLE item
(
    id               uuid        NOT NULL DEFAULT uuid_generate_v4(),
    shopping_list_id uuid        NOT NULL,
    name             varchar(50) NOT NULL,
    quantity         smallint    NOT NULL DEFAULT 1,
    CONSTRAINT item_pk PRIMARY KEY (id),
    CONSTRAINT fk_items_shopping_list FOREIGN KEY (shopping_list_id) REFERENCES shopping_list (id)
);