create schema DEMO

CREATE TABLE DEMO.DOCUMENT
(
  ID   BIGINT IDENTITY PRIMARY KEY,
  CONTAINERNAME VARCHAR(200),
  BLOBNAME VARCHAR(200),
  CONTENTMD5 VARCHAR(200),
  ETAG VARCHAR(200)
);

---

create schema LEGOMODEL

-- The Brick aggregate
CREATE TABLE LEGOMODEL.BRICK
(
  ID   BIGINT IDENTITY PRIMARY KEY,
  DESCRIPTION VARCHAR(200)
);

-- The LegoModel aggregate
CREATE TABLE LEGOMODEL.LEGO_MODEL
(
  ID   BIGINT IDENTITY PRIMARY KEY,
  NAME VARCHAR(200)
);

CREATE TABLE LEGOMODEL.BRICKCONTENT
(
    ID BIGINT IDENTITY PRIMARY KEY,
    LEGOMODEL_ID BIGINT,
    BRICK_ID BIGINT,
    AMOUNT BIGINT,
    FOREIGN KEY (LEGOMODEL_ID) REFERENCES LEGOMODEL.LEGO_MODEL (ID)
--     LEGO_MODEL BIGINT,
--     PRIMARY KEY (LEGO_MODEL, BRICK_ID),
);