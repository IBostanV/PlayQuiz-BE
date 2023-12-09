-- // create_quiz_sequence
ALTER TABLE Q_QUIZ
    ADD CONSTRAINT QUIZ_CATEGORY_FK
        FOREIGN KEY (CAT_ID)
            REFERENCES Q_CATEGORY(CAT_ID)
/execute/


-- //@UNDO
-- SQL to undo the change goes here.
ALTER TABLE Q_QUIZ DROP CONSTRAINT QUIZ_CATEGORY_FK
/execute/
