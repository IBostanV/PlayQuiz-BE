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

-- // create_property_table
CREATE TABLE Q_PROPERTY (
    ID NUMERIC(20,0) NOT NULL,
    NAME VARCHAR(1000) NOT NULL,
    VALUE VARCHAR(800) NOT NULL,
    OLD_VALUE VARCHAR(800)
)/execute/

ALTER TABLE Q_PROPERTY
    ADD CONSTRAINT PK_Q_PROPERTY
        PRIMARY KEY (id)/execute/

CREATE SEQUENCE property_sequence
    START WITH     1
    INCREMENT BY   1
    NOCACHE
    NOCYCLE/execute/

-- //@UNDO
DROP TABLE Q_PROPERTY/execute/
DROP SEQUENCE property_sequence/execute/

