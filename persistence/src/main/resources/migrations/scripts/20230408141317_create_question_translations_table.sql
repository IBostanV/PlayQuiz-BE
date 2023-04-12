-- // create_question_translations_table
CREATE TABLE Q_QUESTION_TRANSLATIONS (
                             TRANSL_ID NUMERIC(20,0) NOT NULL,
                             NAME VARCHAR(150),
                             DESCRIPTION VARCHAR(150) NOT NULL,
                             LANG_ID NUMERIC(20,0) DEFAULT NULL,
                             QUESTION_ID NUMERIC(20,0) NOT NULL
)
/execute/

ALTER TABLE Q_QUESTION_TRANSLATIONS
    ADD CONSTRAINT Q_QUESTION_T_PK
        PRIMARY KEY (TRANSL_ID)
/execute/

-- //@UNDO
DROP TABLE Q_QUESTION_TRANSLATIONS
/execute/


