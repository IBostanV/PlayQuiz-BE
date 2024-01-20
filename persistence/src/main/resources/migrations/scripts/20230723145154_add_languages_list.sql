-- // name_of_the_migration
INSERT INTO Q_LANGUAGE VALUES(1, 'EN', 'English',null, null, trunc(SYSDATE), null)/execute/
INSERT INTO Q_LANGUAGE VALUES(2, 'RU', 'Русский',null, null, trunc(SYSDATE), null)/execute/
INSERT INTO Q_LANGUAGE VALUES(3, 'DE', 'Deutsch',null, null, trunc(SYSDATE), null)/execute/
INSERT INTO Q_LANGUAGE VALUES(4, 'RO', 'Română',null, null, trunc(SYSDATE), null)/execute/

-- //@UNDO
DELETE FROM Q_LANGUAGE WHERE LANG_ID = 1/execute/
DELETE FROM Q_LANGUAGE WHERE LANG_ID = 2/execute/
DELETE FROM Q_LANGUAGE WHERE LANG_ID = 3/execute/
DELETE FROM Q_LANGUAGE WHERE LANG_ID = 4/execute/

