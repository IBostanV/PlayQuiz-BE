-- // create_property_table
CREATE SEQUENCE property_seq
    START WITH     1
    INCREMENT BY   1
    NOCACHE
    NOCYCLE
/execute/

-- //@UNDO
DROP SEQUENCE property_seq
/execute/

