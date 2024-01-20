-- // create_users_history_table
CREATE TABLE Q_USER_HISTORY (
                        HISTORY_ID NUMERIC(20,0) NOT NULL,
                        ACCOUNT_ID NUMERIC(20,0),
                        QUIZ_ID NUMERIC(20,0) NOT NULL,
                        ANSWERS_JSON CLOB,
                        COMPLETED_DATE DATE,
                        SPENT_TIME NUMERIC(20,0) DEFAULT 0,
                        CREATED_BY NUMERIC(20, 0),
                        UPDATED_BY NUMERIC(20, 0),
                        CREATED_DATE DATE NOT NULL,
                        UPDATED_DATE DATE,
                        CONSTRAINT ENFORCE_HISTORY_JSON CHECK (ANSWERS_JSON IS JSON)
)
/execute/

ALTER TABLE Q_USER_HISTORY
    ADD CONSTRAINT Q_USER_HISTORY_PK
        PRIMARY KEY (HISTORY_ID)
/execute/

-- //@UNDO
-- SQL to undo the change goes here.
DROP TABLE Q_USER_HISTORY
/execute/
