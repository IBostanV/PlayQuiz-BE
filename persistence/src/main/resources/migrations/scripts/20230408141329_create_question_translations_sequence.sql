-- // create_question_translations_sequence
CREATE SEQUENCE question_transl_seq
    START WITH     1
    INCREMENT BY   1
    NOCACHE
    NOCYCLE
/execute/


-- //@UNDO
DROP SEQUENCE question_transl_seq
    /execute/


