-- // add_express_constants
INSERT INTO Q_GLOSSARY_TYPE VALUES (0, 'Year', null, 1, null, null, trunc(SYSDATE), null)/execute/

-- //@UNDO
DELETE FROM Q_ANSWER WHERE 1 = 1/execute/
DELETE FROM Q_GLOSSARY WHERE 1 = 1/execute/
DELETE FROM Q_GLOSSARY_TYPE WHERE 1 = 1/execute/