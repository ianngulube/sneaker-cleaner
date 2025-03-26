CREATE TABLE user_table (id BIGSERIAL NOT NULL,
    firstName VARCHAR(255),
    lastName VARCHAR(255),
    email VARCHAR(255),
    msisdn VARCHAR(255),
    pin VARCHAR(255),
    verified BOOLEAN DEFAULT FALSE,
    verificationCode VARCHAR(255),
    PRIMARY KEY (id));
CREATE SEQUENCE user_table_seq START WITH 1 INCREMENT BY 1;