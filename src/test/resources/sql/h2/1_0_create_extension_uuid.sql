SET SCHEMA shopping_list;

CREATE ALIAS generate_ulid AS '
    UUID ez_uuid() {
        return UUID.randomUUID();
    }
';

CREATE ALIAS uuid_generate_v4 AS '
    UUID ez_uuid() {
        return UUID.randomUUID();
    }
';