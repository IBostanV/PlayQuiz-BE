-- // create_roles_sequence
CREATE SEQUENCE roles_seq
    START WITH     1
    INCREMENT BY   1
    NOCACHE
    NOCYCLE
/execute/


-- //@UNDO
DROP SEQUENCE roles_seq
    /execute/

