--
-- File generated with SQLiteStudio v3.2.1 on Sa. März 16 20:50:04 2019
--
-- Text encoding used: UTF-8
--

.print inserting roles

PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

INSERT INTO prj_role (role_id, role_name, role_level) VALUES (1, 'Tester', 1);
INSERT INTO prj_role (role_id, role_name, role_level) VALUES (2, 'UI Designer', 1);
INSERT INTO prj_role (role_id, role_name, role_level) VALUES (3, 'Enterprise Architect', 3);
INSERT INTO prj_role (role_id, role_name, role_level) VALUES (4, 'Senior Architect', 2);
INSERT INTO prj_role (role_id, role_name, role_level) VALUES (5, 'Designer', 1);
INSERT INTO prj_role (role_id, role_name, role_level) VALUES (6, 'Architect', 1);
INSERT INTO prj_role (role_id, role_name, role_level) VALUES (7, 'Principal Developer', 3);
INSERT INTO prj_role (role_id, role_name, role_level) VALUES (8, 'Senior Developer', 2);
INSERT INTO prj_role (role_id, role_name, role_level) VALUES (9, 'Developer', 1);

INSERT INTO app_capability ( cap_version, cap_description, cap_name )
    VALUES ( 2, 'add role data', 'roles dml' );


COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
