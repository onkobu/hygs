BEGIN TRANSACTION;

.print create view v_prj_cap

CREATE VIEW v_cap_cat AS
    SELECT cap.id,
           cap.cat_id,
           cap.name,
           cap.cap_version,
		   cat.name as cat_name
      FROM cap_capability cap
           JOIN
           cap_category cat ON cap.cat_id = cat.id;

INSERT INTO app_capability ( cap_version, cap_description, cap_name )
    VALUES ( 16, 'add joining view for capability and category', 'v_cap_cat ddl' );

COMMIT TRANSACTION;
