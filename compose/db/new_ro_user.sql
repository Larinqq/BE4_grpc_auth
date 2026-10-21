CREATE USER app_read_only WITH PASSWORD 'password';

GRANT CONNECT ON DATABASE app_db TO app_read_only;

GRANT USAGE ON SCHEMA public TO app_read_only;

ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT SELECT ON TABLES TO app_read_only;