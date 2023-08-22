-- // create_glossary_table
CREATE TABLE Q_GLOSSARY_TYPE (
     ID NUMERIC(20,0) NOT NULL,
     NAME VARCHAR(100),
     OPTIONS VARCHAR(100),
     IS_ACTIVE NUMBER(1,0) DEFAULT 0 NOT NULL
)
/execute/

ALTER TABLE Q_GLOSSARY_TYPE
    ADD CONSTRAINT Q_GLOSSARY_TYPE_PK
        PRIMARY KEY (ID)
/execute/

-- //@UNDO
DROP TABLE Q_GLOSSARY_TYPE
/execute/


