-- // create_trophies_table
CREATE TABLE Q_TROPHY (
    TROPHY_ID NUMERIC(20,0) NOT NULL,
    NAME VARCHAR(100) NOT NULL,
    ATTACHMENT BLOB NOT NULL,
    OPTIONS VARCHAR(500)
)
/execute/

ALTER TABLE Q_TROPHY
    ADD CONSTRAINT Q_TROPHY_PK
        PRIMARY KEY (TROPHY_ID)
/execute/

-- //@UNDO
-- SQL to undo the change goes here.
DROP TABLE Q_TROPHY
/execute/


