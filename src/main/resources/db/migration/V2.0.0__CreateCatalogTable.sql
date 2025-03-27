CREATE TABLE catalog_table (id BIGSERIAL NOT NULL,
    offeringName VARCHAR(255) UNIQUE,
    offeringDescription VARCHAR(255) UNIQUE,
    price VARCHAR(255),
    itemType VARCHAR(255),
    PRIMARY KEY (id));
CREATE SEQUENCE catalog_table_seq START WITH 1 INCREMENT BY 1;