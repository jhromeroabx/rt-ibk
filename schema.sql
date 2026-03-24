CREATE TABLE users (
    id VARCHAR(36) NOT NULL,
    name VARCHAR(100),
    email VARCHAR(150) UNIQUE,
    password_hash VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE phone_entity (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    number VARCHAR(20),
    citycode VARCHAR(10),
    countrycode VARCHAR(10),
    user_id VARCHAR(36),
    CONSTRAINT fk_user_phone
        FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE
);

CREATE TABLE idempotency_key (
    request_id VARCHAR(100) PRIMARY KEY,
    response_body TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);