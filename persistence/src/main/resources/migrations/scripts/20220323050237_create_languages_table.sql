-- // create_languages_table
CREATE TABLE Q_LANGUAGES (
                         LANG_ID NUMERIC(20,0) NOT NULL,
                         LANG_CODE VARCHAR(20),
                         NAME VARCHAR(100) NOT NULL
)
    /execute/

ALTER TABLE Q_LANGUAGES
    ADD CONSTRAINT Q_LANGUAGES_PK
        PRIMARY KEY (LANG_ID)
    /execute/

-- //@UNDO
DROP TABLE Q_LANGUAGES
    /execute/
