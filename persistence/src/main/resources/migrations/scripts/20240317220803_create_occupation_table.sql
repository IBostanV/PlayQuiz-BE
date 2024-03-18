-- // add_knowledge_base_table
-- Migration SQL that makes the change goes here.
CREATE TABLE Q_OCCUPATION (
                                  ID NUMERIC(20,0) NOT NULL,
                                  NAME VARCHAR(150),
                                  DOMAIN VARCHAR(150),
                                  PARENT_ID NUMERIC(20, 0),
                                  STATUS VARCHAR(20),
                                  CREATED_BY NUMERIC(20, 0),
                                  UPDATED_BY NUMERIC(20, 0),
                                  CREATED_DATE DATE NOT NULL,
                                  UPDATED_DATE DATE
)
/execute/

ALTER TABLE Q_OCCUPATION
    ADD CONSTRAINT Q_OCCUPATION_PK
        PRIMARY KEY (ID)
/execute/


-- //@UNDO
DROP TABLE Q_OCCUPATION
/execute/


