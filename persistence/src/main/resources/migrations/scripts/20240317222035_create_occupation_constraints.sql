-- // create_category_translations_constraints
ALTER TABLE Q_OCCUPATION
    ADD CONSTRAINT OCCUPATION_PARENT_FK
        FOREIGN KEY (PARENT_ID)
            REFERENCES Q_OCCUPATION(ID)
/execute/

-- //@UNDO
ALTER TABLE Q_OCCUPATION DROP CONSTRAINT OCCUPATION_PARENT_FK
/execute/
