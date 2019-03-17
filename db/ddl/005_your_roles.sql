PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

.print Creating view your_roles

CREATE VIEW your_roles AS
    SELECT p.prj_id,
           prj_name,
           prj_from,
           prj_to,
           prj_company,
           r.role_id,
           r.role_name
      FROM cap_project p
           JOIN
           prj_role_mapping m ON p.prj_id = m.prj_id
           JOIN
           prj_role r ON m.role_id = r.role_id;

INSERT INTO app_capability ( cap_version, cap_description, cap_name )
    VALUES ( 5, 'add view for roles', 'your_roles_view' );

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
