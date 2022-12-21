--
--    Copyright 2010-2016 the original author or authors.
--
--    Licensed under the Apache License, Version 2.0 (the "License");
--    you may not use this file except in compliance with the License.
--    You may obtain a copy of the License at
--
--       http://www.apache.org/licenses/LICENSE-2.0
--
--    Unless required by applicable law or agreed to in writing, software
--    distributed under the License is distributed on an "AS IS" BASIS,
--    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
--    See the License for the specific language governing permissions and
--    limitations under the License.
--

-- // create_verification_tokens
CREATE TABLE Q_VERIFICATION_TOKENS (
    TOKEN_ID NUMERIC(20,0) NOT NULL,
    ACCOUNT_ID NUMERIC(20,0) NOT NULL,
    TOKEN VARCHAR(120),
    ISSUED_DATE DATE NOT NULL,
    VALIDITY_PERIOD NUMBER,
    ACTIVATION_DATE DATE
)
/execute/

ALTER TABLE Q_VERIFICATION_TOKENS
    ADD CONSTRAINT Q_VERIFICATION_TOKENS_PK
        PRIMARY KEY (TOKEN_ID)
/execute/

ALTER TABLE Q_VERIFICATION_TOKENS
    ADD CONSTRAINT Q_USERS_Q_VT_FK
        FOREIGN KEY (ACCOUNT_ID)
            REFERENCES Q_USERS(ACCOUNT_ID)
            ON DELETE CASCADE
/execute/

-- //@UNDO
DROP TABLE Q_VERIFICATION_TOKENS
/execute/

