-- // create_knowledge_base_translation_table
CREATE TABLE Q_KNOWLEDGE_BASE_TRANSLATION (
                                         TRANSL_ID NUMERIC(20,0) NOT NULL,
                                         NAME VARCHAR(150),
                                         DESCRIPTION VARCHAR(150) NOT NULL,
                                         LANG_ID NUMERIC(20,0) DEFAULT NULL,
                                         K_BASE_ID NUMERIC(20,0) NOT NULL
)
/execute/

ALTER TABLE Q_KNOWLEDGE_BASE_TRANSLATION
    ADD CONSTRAINT Q_KNOWLEDGE_BASE_T_PK
        PRIMARY KEY (TRANSL_ID)
/execute/

-- //@UNDO
DROP TABLE Q_KNOWLEDGE_BASE_TRANSLATION
/execute/
