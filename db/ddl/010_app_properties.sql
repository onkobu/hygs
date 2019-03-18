--
-- File generated with SQLiteStudio v3.2.1 on So. März 17 20:01:34 2019
--
-- Text encoding used: UTF-8
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

.print create table app_properties

-- Table: app_properties
CREATE TABLE app_properties (
    prop_id       INTEGER       PRIMARY KEY AUTOINCREMENT
                                NOT NULL,
    prop_name     CHAR (24)     UNIQUE
                                NOT NULL,
    prop_val_text VARCHAR (255),
    prop_val_num  INTEGER,
    prop_val_date DATETIME
);

.print create trigger of app_properties

-- Trigger: app_properties_audit_del
CREATE TRIGGER app_properties_audit_del
         AFTER DELETE
            ON app_properties
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref
                          )
                          VALUES (
                              datetime('now'),
                              'app_properties',
                              'DELETE',
                              old.prop_id
                          );
END;


-- Trigger: app_properties_audit_ins
CREATE TRIGGER app_properties_audit_ins
         AFTER INSERT
            ON app_properties
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref
                          )
                          VALUES (
                              datetime('now'),
                              'app_properties',
                              'INSERT',
                              new.prop_id
                          );
END;


-- Trigger: app_properties_audit_upd
CREATE TRIGGER app_properties_audit_upd
         AFTER UPDATE
            ON app_properties
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref
                          )
                          VALUES (
                              datetime('now'),
                              'app_properties',
                              'UPDATE',
                              new.prop_id
                          );
END;

INSERT INTO app_capability ( cap_version, cap_description, cap_name )
    VALUES ( 10, 'add application properties', 'properties ddl' );


COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
