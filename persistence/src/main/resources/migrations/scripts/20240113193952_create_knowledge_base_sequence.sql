-- // create_knowledge_base_sequence
CREATE SEQUENCE knowledge_base_seq
    START WITH     1
    INCREMENT BY   1
    NOCACHE
    NOCYCLE
/execute/


-- //@UNDO
DROP SEQUENCE knowledge_base_seq
/execute/


