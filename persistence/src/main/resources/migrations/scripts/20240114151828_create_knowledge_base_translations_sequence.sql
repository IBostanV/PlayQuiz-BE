-- // create_knowledge_base_translations_sequence
CREATE SEQUENCE knowledge_base_transl_seq
    START WITH     1
    INCREMENT BY   1
    NOCACHE
    NOCYCLE
/execute/


-- //@UNDO
DROP SEQUENCE knowledge_base_transl_seq
/execute/

