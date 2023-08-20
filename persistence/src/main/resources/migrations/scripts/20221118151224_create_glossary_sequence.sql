-- // create_glossary_sequence
CREATE SEQUENCE glossaries_seq
    START WITH     1
    INCREMENT BY   1
    NOCACHE
    NOCYCLE
/execute/


-- //@UNDO
DROP SEQUENCE glossaries_seq
/execute/


