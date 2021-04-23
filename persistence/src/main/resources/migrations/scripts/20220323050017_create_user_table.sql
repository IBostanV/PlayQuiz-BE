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
CREATE TABLE V_USER (
                      ID NUMERIC(20,0) NOT NULL,
                      EMAIL VARCHAR(100) NOT NULL,
                      PASSWORD VARCHAR(100) NOT NULL
)/execute/

ALTER TABLE V_USER
    ADD CONSTRAINT PK_V_USER
        PRIMARY KEY (id)/execute/

-- //@UNDO
DROP TABLE V_USER/execute/



