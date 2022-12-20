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

-- // create_glossary_sequence
ALTER TABLE Q_GLOSSARIES
    ADD CONSTRAINT GLOSSARY_CATEGORY_FK
        FOREIGN KEY (CAT_ID)
            REFERENCES Q_CATEGORIES(CAT_ID)
/execute/

ALTER TABLE Q_GLOSSARIES
    ADD CONSTRAINT GLOSSARY_PARENT_FK
        FOREIGN KEY (PARENT_ID)
            REFERENCES Q_GLOSSARIES(TERM_ID)
/execute/


-- //@UNDO
-- SQL to undo the change goes here.
ALTER TABLE Q_GLOSSARIES DROP CONSTRAINT GLOSSARY_CATEGORY_FK
/execute/
ALTER TABLE Q_GLOSSARIES DROP CONSTRAINT GLOSSARY_PARENT_FK
/execute/
