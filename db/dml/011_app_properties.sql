--
-- File generated with SQLiteStudio v3.2.1 on So. März 17 20:01:34 2019
--
-- Text encoding used: UTF-8
--
BEGIN TRANSACTION;

.print insert app_properties

INSERT INTO app_properties (prop_id, prop_name, prop_val_text, prop_val_num, prop_val_date) VALUES (1, 'language', 'de', NULL, NULL);
INSERT INTO app_properties (prop_id, prop_name, prop_val_text, prop_val_num, prop_val_date) VALUES (2, 'last_backup', NULL, NULL, NULL);

INSERT INTO app_capability ( cap_version, cap_description, cap_name )
    VALUES ( 11, 'add application properties', 'properties ddl' );


COMMIT TRANSACTION;
