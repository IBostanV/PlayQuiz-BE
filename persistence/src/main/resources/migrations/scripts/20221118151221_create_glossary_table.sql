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

-- // create_glossary_table
CREATE TABLE Q_GLOSSARIES (
     TERM_ID NUMERIC(20,0) NOT NULL,
     KEY VARCHAR(100) NOT NULL,
     VALUE VARCHAR(100),
     CAT_ID NUMERIC(20,0) NOT NULL,
     ATTACHMENT BLOB,
     OPTIONS VARCHAR(100),
     PARENT_ID NUMERIC(20,0) DEFAULT NULL,
     IS_ACTIVE NUMBER(1,0) DEFAULT 0 NOT NULL
)
/execute/

ALTER TABLE Q_GLOSSARIES
    ADD CONSTRAINT Q_GLOSSARIES_PK
        PRIMARY KEY (TERM_ID)
/execute/

-- //@UNDO
-- SQL to undo the change goes here.
DROP TABLE Q_GLOSSARIES
/execute/


