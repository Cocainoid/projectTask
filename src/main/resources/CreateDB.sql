DROP TABLE IF EXISTS ACCOUNT;

CREATE TABLE IF NOT EXISTS ACCOUNT
(
    ID BIGINT NOT NULL,
    ACCOUNT_NUMBER VARCHAR(20) NOT NULL,
    ACCOUNT_BALANCE FLOAT NOT NULL,
    CONSTRAINT ACCOUNT_PKEY PRIMARY KEY (ACCOUNT_NUMBER)
);

DROP TABLE IF EXISTS CARD;

CREATE TABLE IF NOT EXISTS CARD
(
    ID BIGINT NOT NULL AUTO_INCREMENT,
    CARD_NUMBER BIGINT NOT NULL,
    ACCOUNT_NUMBER VARCHAR NOT NULL,
    CONSTRAINT CARD_PKEY PRIMARY KEY (CARD_NUMBER)
);