-- // create_quizzes_sequence
CREATE SEQUENCE quizzes_seq
    START WITH     1
    INCREMENT BY   1
    NOCACHE
    NOCYCLE
/execute/


-- //@UNDO
DROP SEQUENCE quizzes_seq
/execute/


