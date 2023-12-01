-- // create_quiz_table
CREATE TABLE Q_QUIZ (
    QUIZ_ID NUMERIC(20,0) NOT NULL,
    CAT_ID NUMERIC(20,0) NOT NULL,
    QUESTION_IDS VARCHAR(100),
    CREATED_DATE DATE NOT NULL,
    UPDATED_DATE DATE,
    QUESTIONS_COUNT NUMERIC(20,0)
)
/execute/

ALTER TABLE Q_QUIZ
    ADD CONSTRAINT Q_QUIZ_PK
        PRIMARY KEY (QUIZ_ID)
/execute/

-- //@UNDO
-- SQL to undo the change goes here.
DROP TABLE Q_QUIZ
/execute/


