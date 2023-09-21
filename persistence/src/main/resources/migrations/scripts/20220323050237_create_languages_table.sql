-- // create_languages_table
CREATE TABLE Q_LANGUAGE (
                         LANG_ID NUMERIC(20,0) NOT NULL,
                         LANG_CODE VARCHAR(20),
                         NAME VARCHAR(100) NOT NULL
)
    /execute/

ALTER TABLE Q_LANGUAGE
    ADD CONSTRAINT Q_LANGUAGE_PK
        PRIMARY KEY (LANG_ID)
    /execute/

-- //@UNDO
DROP TABLE Q_LANGUAGE
    /execute/
