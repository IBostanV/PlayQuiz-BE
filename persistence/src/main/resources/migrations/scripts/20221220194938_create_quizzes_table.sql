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

-- // create_quizzes_table
CREATE TABLE Q_QUIZZES (
    QUIZ_ID NUMERIC(20,0) NOT NULL,
    CAT_ID NUMERIC(20,0) NOT NULL,
    QUESTION_IDS VARCHAR(100),
    CREATED_DATE DATE NOT NULL,
    UPDATED_DATE DATE,
    QUESTIONS_COUNT NUMERIC(20,0)
)
/execute/

ALTER TABLE Q_QUIZZES
    ADD CONSTRAINT Q_QUIZZES_PK
        PRIMARY KEY (QUIZ_ID)
/execute/

-- //@UNDO
-- SQL to undo the change goes here.
DROP TABLE Q_QUIZZES
/execute/


