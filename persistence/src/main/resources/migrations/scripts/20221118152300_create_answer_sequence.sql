-- // create_answer_sequence
CREATE SEQUENCE answers_seq
    START WITH     1
    INCREMENT BY   1
    NOCACHE
    NOCYCLE
/execute/

-- //@UNDO
DROP SEQUENCE answers_seq
/execute/


