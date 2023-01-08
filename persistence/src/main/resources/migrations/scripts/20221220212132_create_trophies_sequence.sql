-- // create_trophies_sequence
CREATE SEQUENCE trophies_seq
    START WITH     1
    INCREMENT BY   1
    NOCACHE
    NOCYCLE
/execute/


-- //@UNDO
DROP SEQUENCE trophies_seq
/execute/


