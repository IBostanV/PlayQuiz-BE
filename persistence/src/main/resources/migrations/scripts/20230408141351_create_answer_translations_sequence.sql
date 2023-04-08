-- // create_answer_translations_sequence
CREATE SEQUENCE answer_transl_seq
    START WITH     1
    INCREMENT BY   1
    NOCACHE
    NOCYCLE
/execute/


-- //@UNDO
DROP SEQUENCE answer_transl_seq
    /execute/


