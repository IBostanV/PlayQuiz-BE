-- // add_knowledge_base_table
-- Migration SQL that makes the change goes here.
CREATE TABLE Q_KNOWLEDGE_BASE (
                            ID NUMERIC(20,0) NOT NULL,
                            CAT_ID NUMERIC(20, 0) NOT NULL,
                            TITLE VARCHAR(150),
                            CONTENT VARCHAR(4000),
                            PARENT_ID NUMERIC(20, 0),
                            ATTACHMENT BLOB,
                            VISIBLE NUMERIC(1, 0) DEFAULT 1,
                            UPVOTES NUMERIC(20, 0),
                            DOWNVOTES NUMERIC(20, 0),
                            TAGS VARCHAR(300),
                            STATUS VARCHAR(20),
                            CREATED_BY NUMERIC(20, 0),
                            UPDATED_BY NUMERIC(20, 0),
                            CREATED_DATE DATE NOT NULL,
                            UPDATED_DATE DATE
)
/execute/

ALTER TABLE Q_KNOWLEDGE_BASE
    ADD CONSTRAINT Q_KNOWLEDGE_BASE_PK
        PRIMARY KEY (ID)
/execute/


-- //@UNDO
DROP TABLE Q_KNOWLEDGE_BASE
/execute/


