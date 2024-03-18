-- // create_knowledge_base_sequence
CREATE SEQUENCE occupation_seq
    START WITH     1
    INCREMENT BY   1
    NOCACHE
    NOCYCLE
/execute/


-- //@UNDO
DROP SEQUENCE occupation_seq
/execute/


