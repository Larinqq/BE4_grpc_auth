CREATE TABLE users
(
    id            uuid PRIMARY KEY,
    email         VARCHAR UNIQUE NOT NULL,
    password_hash VARCHAR        NOT NULL,
    first_name    VARCHAR        NOT NULL,
    last_name     VARCHAR        NOT NULL,
    phone_number  VARCHAR        NOT NULL
);

CREATE TABLE refresh_tokens
(
    id         uuid PRIMARY KEY,
    user_id    uuid                     NOT NULL UNIQUE REFERENCES users (id) ON DELETE CASCADE,
    expires_at TIMESTAMP WITH TIME ZONE NOT NULL
);


