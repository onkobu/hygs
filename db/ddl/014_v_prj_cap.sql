BEGIN TRANSACTION;

.print create view v_prj_cap

CREATE VIEW v_prj_cap AS
    SELECT p.prj_id,
           c.cap_id,
           cap_name,
           assigned_weight,
           cap_version
      FROM cap_project p
           JOIN
           prj_cap_mapping m ON p.prj_id = m.prj_id
           JOIN
           cap_capability c ON m.cap_id = c.cap_id;

INSERT INTO app_capability ( cap_version, cap_description, cap_name )
    VALUES ( 14, 'add joining view for project-capability', 'v_prj_cap ddl' );

COMMIT TRANSACTION;
