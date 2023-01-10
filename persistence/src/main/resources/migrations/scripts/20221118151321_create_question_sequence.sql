-- // create_question_sequence
CREATE SEQUENCE questions_seq
    START WITH     1
    INCREMENT BY   1
    NOCACHE
    NOCYCLE
/execute/

-- //@UNDO
DROP SEQUENCE questions_seq
/execute/


