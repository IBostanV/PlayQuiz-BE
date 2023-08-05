-- // create_trophies_table
CREATE TABLE Q_TOPIC (
                            ID NUMERIC(20,0) NOT NULL,
                            NAME VARCHAR(100) NOT NULL
)
    /execute/

ALTER TABLE Q_TOPIC
    ADD CONSTRAINT Q_TOPIC_PK
        PRIMARY KEY (ID)
    /execute/

-- //@UNDO
-- SQL to undo the change goes here.
DROP TABLE Q_TOPIC
    /execute/
