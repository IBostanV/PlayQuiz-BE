-- // name_of_the_migration
INSERT INTO Q_ROLES VALUES(1, 'ROLE_ADMIN')/execute/
INSERT INTO Q_ROLES VALUES(2, 'ROLE_USER')/execute/

-- //@UNDO
DELETE FROM Q_ROLES WHERE ROLE_ID = 1/execute/
DELETE FROM Q_ROLES WHERE ROLE_ID = 2/execute/


