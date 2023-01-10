-- // create_user_history_sequence
CREATE SEQUENCE user_history_seq
    START WITH     1
    INCREMENT BY   1
    NOCACHE
    NOCYCLE
/execute/


-- //@UNDO
DROP SEQUENCE user_history_seq
/execute/

