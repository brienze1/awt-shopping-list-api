SET SCHEMA 'shopping_list';

DROP EXTENSION IF EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "uuid-ossp" with schema shopping_list;