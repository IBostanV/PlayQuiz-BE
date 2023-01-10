-- // create_verification_tokens
CREATE SEQUENCE verification_tokens_seq
    START WITH     1
    INCREMENT BY   1
    NOCACHE
    NOCYCLE
/execute/

-- //@UNDO
DROP SEQUENCE verification_tokens_seq
/execute/


