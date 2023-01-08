-- // create_user_sequence
CREATE SEQUENCE USERS_SEQ
    START WITH     1
    INCREMENT BY   1
    NOCACHE
    NOCYCLE
/execute/


-- //@UNDO
DROP SEQUENCE USERS_SEQ
    /execute/
