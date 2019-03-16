--
-- File generated with SQLiteStudio v3.2.1 on Sa. März 16 20:55:55 2019
--
-- Text encoding used: UTF-8
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

.print table prj_role_mapping

-- Table: prj_role_mapping
CREATE TABLE prj_role_mapping (
    prj_id  INTEGER REFERENCES cap_project (prj_id) 
                    NOT NULL,
    role_id INTEGER REFERENCES prj_role (role_id) 
                    NOT NULL,
    UNIQUE (
        prj_id,
        role_id
    )
);

INSERT INTO app_capability ( cap_version, cap_description, cap_name )
    VALUES ( 3, 'add role-project mapping table', 'prj_role_map' );


COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
