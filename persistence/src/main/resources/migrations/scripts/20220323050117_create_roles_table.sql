-- // create_roles_table
CREATE TABLE Q_ROLE (
    ROLE_ID NUMERIC(20,0) NOT NULL,
    NAME VARCHAR(100) NOT NULL
)
/execute/

ALTER TABLE Q_ROLE
    ADD CONSTRAINT Q_USER_ROLE_PK
        PRIMARY KEY (ROLE_ID)
/execute/

-- //@UNDO
DROP TABLE Q_ROLE
/execute/



