-- // create_category_translations_sequence
CREATE SEQUENCE cat_transl_seq
    START WITH     1
    INCREMENT BY   1
    NOCACHE
    NOCYCLE
/execute/


-- //@UNDO
DROP SEQUENCE cat_transl_seq
    /execute/


