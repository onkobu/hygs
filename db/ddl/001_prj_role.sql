--
-- File generated with SQLiteStudio v3.2.1 on Sa. März 16 20:49:44 2019
--
-- Text encoding used: UTF-8
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

.print table prj_role

-- Table: prj_role
CREATE TABLE prj_role (
    role_id    INTEGER      PRIMARY KEY AUTOINCREMENT
                            NOT NULL,
    role_name  VARCHAR (64) UNIQUE
                            NOT NULL,
    role_level INTEGER      NOT NULL
);

.print triggers on prj_role

-- Trigger: prj_role_audit_upd
CREATE TRIGGER prj_role_audit_upd
         AFTER UPDATE
            ON prj_role
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref
                          )
                          VALUES (
                              datetime('now'),
                              'prj_role',
                              'UPDATE',
                              new.role_id
                          );
END;


-- Trigger: prj_role_audit_ins
CREATE TRIGGER prj_role_audit_ins
         AFTER INSERT
            ON prj_role
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref
                          )
                          VALUES (
                              datetime('now'),
                              'prj_role',
                              'INSERT',
                              new.role_id
                          );
END;


-- Trigger: prj_role_audit_del
CREATE TRIGGER prj_role_audit_del
         AFTER DELETE
            ON prj_role
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref
                          )
                          VALUES (
                              datetime('now'),
                              'prj_role',
                              'DELETE',
                              old.role_id
                          );
END;


INSERT INTO app_capability ( cap_version, cap_description, cap_name )
    VALUES ( 1, 'add role table', 'roles ddl' );

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
