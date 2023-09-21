-- // create_user_table
CREATE TABLE Q_USER (
                      ACCOUNT_ID NUMERIC(20,0) NOT NULL,
                      NAME VARCHAR(100) DEFAULT '',
                      EMAIL VARCHAR(100) NOT NULL,
                      PASSWORD VARCHAR(100) NOT NULL,
                      BIRTHDAY DATE,
                      IS_ENABLED NUMBER(1,0) DEFAULT 0 NOT NULL
)
/execute/

ALTER TABLE Q_USER
    ADD CONSTRAINT Q_USER_PK
        PRIMARY KEY (ACCOUNT_ID)
/execute/

-- //@UNDO
DROP TABLE Q_USER
/execute/



