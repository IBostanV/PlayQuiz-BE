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

-- // create_user_table
CREATE TABLE Q_USERS_ROLES (
    ACCOUNT_ID NUMERIC(20, 0) REFERENCES Q_USERS(ACCOUNT_ID) ON DELETE CASCADE,
    ROLE_ID NUMERIC(20,0) REFERENCES Q_ROLES(ROLE_ID) ON DELETE CASCADE
)
/execute/

ALTER TABLE Q_USERS_ROLES
    ADD CONSTRAINT Q_USERS_ROLES_PK
        PRIMARY KEY (ACCOUNT_ID, ROLE_ID)
/execute/

-- //@UNDO
DROP TABLE Q_USERS_ROLES
/execute/



