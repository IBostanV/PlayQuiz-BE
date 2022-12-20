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

-- // create_users_history_table
CREATE TABLE Q_USER_HISTORIES (
                        HISTORY_ID NUMERIC(20,0) NOT NULL,
                        ACCOUNT_ID NUMERIC(20,0),
                        QUIZ_ID NUMERIC(20,0) NOT NULL,
                        ANSWERS_JSON CLOB,
                        COMPLETED_DATE DATE,
                        SPENT_TIME NUMERIC(20,0) DEFAULT 0,
                        EARNED_TROPHIES NUMERIC(20,0),
                        CONSTRAINT ENFORCE_HISTORY_JSON CHECK (ANSWERS_JSON IS JSON)
)
/execute/

ALTER TABLE Q_USER_HISTORIES
    ADD CONSTRAINT Q_USER_HISTORIES_PK
        PRIMARY KEY (HISTORY_ID)
/execute/

-- //@UNDO
-- SQL to undo the change goes here.
DROP TABLE Q_USER_HISTORIES
/execute/
