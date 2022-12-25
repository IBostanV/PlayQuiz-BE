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

-- // create_categories_sequence
ALTER TABLE Q_CATEGORIES
    ADD CONSTRAINT SUBCATEGORIES_CATEGORIES_FK
        FOREIGN KEY (SUBCATEGORY_ID)
            REFERENCES Q_CATEGORIES(CAT_ID)
            ON DELETE CASCADE
/execute/

ALTER TABLE Q_CATEGORIES
    ADD CONSTRAINT NAME_UNIQUE
        UNIQUE (NAME)
/execute/


-- //@UNDO
-- SQL to undo the change goes here.
ALTER TABLE Q_CATEGORIES DROP CONSTRAINT SUBCATEGORIES_CATEGORIES_FK
/execute/
ALTER TABLE Q_CATEGORIES DROP CONSTRAINT NAME_UNIQUE
/execute/
