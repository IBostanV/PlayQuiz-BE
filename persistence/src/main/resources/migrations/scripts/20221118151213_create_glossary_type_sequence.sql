-- // create_glossary_sequence
CREATE SEQUENCE glossary_type_seq
    START WITH     1
    INCREMENT BY   1
    NOCACHE
    NOCYCLE
/execute/


-- //@UNDO
DROP SEQUENCE glossary_type_seq
/execute/


