-- // create_user_table
CREATE TABLE Q_USER_ROLES (
    ACCOUNT_ID NUMERIC(20, 0) REFERENCES Q_USER(ACCOUNT_ID) ON DELETE CASCADE,
    ROLE_ID NUMERIC(20,0) REFERENCES Q_ROLE(ROLE_ID) ON DELETE CASCADE
)
/execute/

ALTER TABLE Q_USER_ROLES
    ADD CONSTRAINT Q_USER_ROLES_PK
        PRIMARY KEY (ACCOUNT_ID, ROLE_ID)
/execute/

-- //@UNDO
DROP TABLE Q_USER_ROLES
/execute/



