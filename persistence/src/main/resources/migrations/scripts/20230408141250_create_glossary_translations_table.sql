-- // create_glossary_translations_table
CREATE TABLE Q_GLOSSARY_TRANSLATIONS (
                              TRANSL_ID NUMERIC(20,0) NOT NULL,
                              NAME VARCHAR(150),
                              DESCRIPTION VARCHAR(150) NOT NULL,
                              LANG_ID NUMERIC(20,0) DEFAULT NULL,
                              TERM_ID NUMERIC(20,0) NOT NULL
)
/execute/

ALTER TABLE Q_GLOSSARY_TRANSLATIONS
    ADD CONSTRAINT Q_GLOSSARY_T_PK
        PRIMARY KEY (TRANSL_ID)
/execute/

-- //@UNDO
DROP TABLE Q_GLOSSARY_TRANSLATIONS
/execute/

