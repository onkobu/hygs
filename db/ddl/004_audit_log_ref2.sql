--
-- File generated with SQLiteStudio v3.2.1 on Sa. März 16 20:55:55 2019
--
-- Text encoding used: UTF-8
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

.print modifying table log_audit

-- Table: prj_cap_mapping_mapping
ALTER TABLE log_audit ADD COLUMN aud_ref2 INTEGER;  

.print triggers

-- Trigger: prj_cap_mapping_audit_upd
CREATE TRIGGER prj_cap_mapping_audit_upd
         AFTER UPDATE
            ON prj_cap_mapping
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref,
							  aud_ref2
                          )
                          VALUES (
                              datetime('now'),
                              'prj_cap_mapping',
                              'UPDATE',
                              new.prj_id,
							  new.cap_Id
                          );
END;


-- Trigger: prj_cap_mapping_audit_ins
CREATE TRIGGER prj_cap_mapping_audit_ins
         AFTER INSERT
            ON prj_cap_mapping
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref,
							  aud_ref2
                          )
                          VALUES (
                              datetime('now'),
                              'prj_cap_mapping',
                              'INSERT',
                              new.prj_id,
							  new.cap_id
                          );
END;


-- Trigger: prj_cap_mapping_audit_del
CREATE TRIGGER prj_cap_mapping_audit_del
         AFTER DELETE
            ON prj_cap_mapping
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref,
							  aud_ref2
                          )
                          VALUES (
                              datetime('now'),
                              'prj_cap_mapping',
                              'DELETE',
                              old.prj_id,
							  old.cap_id
                          );
END;

-- Trigger: prj_cap_mapping_audit_upd
CREATE TRIGGER prj_role_mapping_audit_upd
         AFTER UPDATE
            ON prj_role_mapping
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref,
							  aud_ref2
                          )
                          VALUES (
                              datetime('now'),
                              'prj_role_mapping',
                              'UPDATE',
                              new.prj_id,
							  new.role_id
                          );
END;


-- Trigger: prj_cap_mapping_audit_ins
CREATE TRIGGER prj_role_mapping_audit_ins
         AFTER INSERT
            ON prj_role_mapping
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref,
							  aud_ref2
                          )
                          VALUES (
                              datetime('now'),
                              'prj_role_mapping',
                              'INSERT',
                              new.prj_id,
							  new.role_id
                          );
END;


-- Trigger: prj_cap_mapping_audit_del
CREATE TRIGGER prj_role_mapping_audit_del
         AFTER DELETE
            ON prj_role_mapping
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref,
							  aud_ref2
                          )
                          VALUES (
                              datetime('now'),
                              'prj_role_mapping',
                              'DELETE',
                              old.prj_id,
							  old.role_id
                          );
END;

INSERT INTO app_capability ( cap_version, cap_description, cap_name )
    VALUES ( 4, 'audit log for mapping tables', 'log_audit_ref2' );


COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
