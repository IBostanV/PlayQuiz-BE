-- // create_languages_sequence
CREATE SEQUENCE languages_seq
    START WITH     1
    INCREMENT BY   1
    NOCACHE
    NOCYCLE
/execute/


-- //@UNDO
DROP SEQUENCE languages_seq
    /execute/

