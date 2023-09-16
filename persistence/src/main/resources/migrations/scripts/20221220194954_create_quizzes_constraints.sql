-- // create_quizzes_sequence
ALTER TABLE Q_QUESTIONZES
    ADD CONSTRAINT QUIZZES_CATEGORY_FK
        FOREIGN KEY (CAT_ID)
            REFERENCES Q_CATEGORY(CAT_ID)
/execute/


-- //@UNDO
-- SQL to undo the change goes here.
ALTER TABLE Q_QUESTIONZES DROP CONSTRAINT QUIZZES_CATEGORY_FK
/execute/
