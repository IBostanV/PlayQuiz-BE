-- // create_user_category_table
CREATE TABLE Q_USER_CATEGORY
(
    ACCOUNT_ID NUMERIC(20, 0) NOT NULL,
    CAT_ID     NUMERIC(20, 0) NOT NULL
)
/execute/

ALTER TABLE Q_USER_CATEGORY
    ADD CONSTRAINT Q_USER_CATEGORY_PK
        PRIMARY KEY (ACCOUNT_ID, CAT_ID)
/execute/

-- //@UNDO
DROP TABLE Q_USER_CATEGORY
/execute/
