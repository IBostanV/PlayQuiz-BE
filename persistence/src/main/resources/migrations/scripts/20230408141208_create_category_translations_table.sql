-- // create_category_translations_table
CREATE TABLE Q_CATEGORY_TRANSLATIONS (
                              TRANSL_ID NUMERIC(20,0) NOT NULL,
                              NAME VARCHAR(150),
                              DESCRIPTION VARCHAR(150) NOT NULL,
                              LANG_ID NUMERIC(20,0) DEFAULT NULL,
                              CAT_ID NUMERIC(20,0) NOT NULL
)
/execute/

ALTER TABLE Q_CATEGORY_TRANSLATIONS
    ADD CONSTRAINT Q_CATEGORY_TRANSLATIONS_PK
        PRIMARY KEY (TRANSL_ID)
    /execute/

-- //@UNDO
DROP TABLE Q_CATEGORY_TRANSLATIONS
    /execute/


