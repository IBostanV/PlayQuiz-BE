INSERT INTO Q_USER
(ACCOUNT_ID, USERNAME, NAME, SURNAME, AVATAR, TROPHY_ID, EXPERIENCE, LANG_ID, THEME, EMAIL, PASSWORD, BIRTHDAY, IS_ENABLED)
VALUES(1, 'vanyok93', 'Ion', 'Bostan', NULL, NULL, NULL, 1, 'Dark', 'email@gmail.com', '{bcrypt}$2a$10$FvZ5JaeKN2nU84HiRiXBq.saBPaxdxrd1d7wkJf6o0gpSxJEjkXH2', TIMESTAMP '1993-05-26 00:00:00.000000', 1);

INSERT INTO Q_USER
(ACCOUNT_ID, USERNAME, NAME, SURNAME, AVATAR, TROPHY_ID, EXPERIENCE, LANG_ID, THEME, EMAIL, PASSWORD, BIRTHDAY, IS_ENABLED)
VALUES(2, 'vanyok9i', 'Ion', 'Bostan', NULL, NULL, NULL, 1, 'Light', 'vanyok93@yahoo.com', '{bcrypt}$2a$10$L9IiZyiIw3mQ8RNZ9/7w.uHgS1bUv8xK.3nIXoEQgRKxvYeMVE5SS', TIMESTAMP '1993-05-26 00:00:00.000000', 0);

INSERT INTO Q_USER_ROLES (ACCOUNT_ID, ROLE_ID)
VALUES(1, 2);