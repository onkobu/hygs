PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

.print table cap_capability

ALTER TABLE cap_capability ADD COLUMN cap_deleted BOOLEAN DEFAULT FALSE;

.print table cap_category

ALTER TABLE cap_category ADD COLUMN cat_deleted BOOLEAN DEFAULT FALSE;

.print table cap_project

ALTER TABLE cap_project ADD COLUMN prj_deleted BOOLEAN DEFAULT FALSE;

.print table cap_skill

ALTER TABLE cap_skill ADD COLUMN cap_deleted BOOLEAN DEFAULT FALSE;

.print table prj_company

ALTER TABLE prj_company ADD COLUMN cmp_deleted BOOLEAN DEFAULT FALSE;

.print table prj_role

ALTER TABLE prj_role ADD COLUMN role_deleted BOOLEAN DEFAULT FALSE;

INSERT INTO app_capability ( cap_version, cap_description, cap_name )
    VALUES ( 15, 'add deleted column', 'deleted column' );

END TRANSACTION;
