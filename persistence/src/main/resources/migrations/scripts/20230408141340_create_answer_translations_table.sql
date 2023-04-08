-- // create_answer_translations_table
CREATE TABLE Q_ANSWER_TRANSLATIONS (
                                         TRANSL_ID NUMERIC(20,0) NOT NULL,
                                         NAME VARCHAR(150),
                                         DESCRIPTION VARCHAR(150) NOT NULL,
                                         LANG_ID NUMERIC(20,0) DEFAULT NULL,
                                         ANS_ID NUMERIC(20,0) NOT NULL
)
    /execute/

ALTER TABLE Q_ANSWER_TRANSLATIONS
    ADD CONSTRAINT Q_ANSWER_T_PK
        PRIMARY KEY (TRANSL_ID)
    /execute/

-- //@UNDO
DROP TABLE Q_ANSWER_TRANSLATIONS
    /execute/


