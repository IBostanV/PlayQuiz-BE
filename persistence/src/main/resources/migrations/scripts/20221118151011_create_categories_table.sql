-- // create_categories_table
CREATE TABLE Q_CATEGORY (
     CAT_ID NUMERIC(20,0) NOT NULL,
     NAME VARCHAR(150),
     NATURAL_ID VARCHAR(150) NOT NULL,
     SUBCATEGORY_ID NUMERIC(20,0) DEFAULT NULL,
     VISIBLE NUMERIC(1, 0) DEFAULT 1
)
/execute/

ALTER TABLE Q_CATEGORY
    ADD CONSTRAINT Q_CATEGORY_PK
        PRIMARY KEY (CAT_ID)
/execute/

-- //@UNDO
-- SQL to undo the change goes here.
DROP TABLE Q_CATEGORY
/execute/


