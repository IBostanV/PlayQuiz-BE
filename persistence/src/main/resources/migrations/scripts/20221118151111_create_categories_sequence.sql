-- // create_categories_sequence
CREATE SEQUENCE categories_seq
    START WITH     1
    INCREMENT BY   1
    NOCACHE
    NOCYCLE
/execute/


-- //@UNDO
DROP SEQUENCE categories_seq
/execute/


