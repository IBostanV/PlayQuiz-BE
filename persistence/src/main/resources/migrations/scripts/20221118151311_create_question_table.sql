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

-- // create_question_table
CREATE TABLE Q_QUESTIONS (
    QUESTION_ID NUMERIC(20,0) NOT NULL,
    ACCOUNT_ID NUMERIC(20,0) DEFAULT 1 NOT NULL,
    TYPE VARCHAR(50) NOT NULL,
    TIP_ID NUMERIC(20,0),
    CAT_ID NUMERIC(20,0) NOT NULL,
    IS_ACTIVE NUMBER(1,0) NOT NULL,
    COMPLEXITY_LEVEL NUMERIC(10,0),
    CONTENT VARCHAR(1000) NOT NULL,
    CREATED_DATE DATE NOT NULL,
    UPDATED_DATE DATE,
    UPDATE_ACCOUNT_ID NUMERIC(20,0),
    TOPIC VARCHAR(200),
    PRIORITY NUMERIC(20,0),
    ATTRIBUTES VARCHAR(500)
)
/execute/

ALTER TABLE Q_QUESTIONS
    ADD CONSTRAINT Q_QUESTIONS_PK
        PRIMARY KEY (QUESTION_ID)
/execute/

-- //@UNDO
DROP TABLE Q_QUESTIONS
/execute/


