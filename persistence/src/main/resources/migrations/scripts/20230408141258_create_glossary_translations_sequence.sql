-- // create_glossary_translations_sequence
CREATE SEQUENCE term_transl_seq
    START WITH     1
    INCREMENT BY   1
    NOCACHE
    NOCYCLE
/execute/


-- //@UNDO
DROP SEQUENCE term_transl_seq
    /execute/


