--
-- File generated with SQLiteStudio v3.2.1 on So. März 17 15:07:30 2019
--
-- Text encoding used: UTF-8
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

.print Creating table cap_skill

-- Table: cap_skill
CREATE TABLE cap_skill (
    skill_id          INTEGER       PRIMARY KEY AUTOINCREMENT
                                    NOT NULL,
    skill_name        VARCHAR (64)  NOT NULL
                                    UNIQUE,
    skill_description VARCHAR (255),
    skill_level       INTEGER       NOT NULL
);


-- Trigger: cap_skill_audit_ins
CREATE TRIGGER cap_skill_audit_ins
         AFTER INSERT
            ON cap_skill
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref
                          )
                          VALUES (
                              datetime('now'),
                              'cap_skill',
                              'INSERT',
                              new.skill_id
                          );
END;


-- Trigger: cap_skill_audit_del
CREATE TRIGGER cap_skill_audit_del
         AFTER DELETE
            ON cap_skill
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref
                          )
                          VALUES (
                              datetime('now'),
                              'cap_skill',
                              'DELETE',
                              old.skill_id
                          );
END;


-- Trigger: cap_skill_audit_upd
CREATE TRIGGER cap_skill_audit_upd
         AFTER UPDATE
            ON cap_skill
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref
                          )
                          VALUES (
                              datetime('now'),
                              'cap_skill',
                              'UPDATE',
                              new.skill_id
                          );
END;

.print Creating table cap_skill_mapping

-- Table: cap_skill_mapping
CREATE TABLE cap_skill_mapping (
    cap_id      INTEGER REFERENCES cap_capability (cap_id),
    skill_id    INTEGER REFERENCES cap_skill (skill_id),
    skill_since DATE    NOT NULL,
    UNIQUE (
        cap_id,
        skill_id,
        skill_since
    )
);


-- Trigger: cap_skill_map_audit_del
CREATE TRIGGER cap_skill_map_audit_del
         AFTER DELETE
            ON cap_skill_mapping
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
                              'cap_skill_mapping',
                              'DELETE',
                              old.skill_id,
                              old.cap_id
                          );
END;


-- Trigger: cap_skill_map_audit_ins
CREATE TRIGGER cap_skill_map_audit_ins
         AFTER INSERT
            ON cap_skill_mapping
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
                              'cap_skill_mapping',
                              'INSERT',
                              new.skill_id,
                              new.cap_id
                          );
END;


-- Trigger: cap_skill_map_audit_upd
CREATE TRIGGER cap_skill_map_audit_upd
         AFTER UPDATE
            ON cap_skill_mapping
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
                              'cap_skill_mapping',
                              'UPDATE',
                              new.skill_id,
                              new.cap_id
                          );
END;


INSERT INTO app_capability ( cap_version, cap_description, cap_name )
    VALUES ( 6, 'add skill mapping', 'cap_skill_map' );


COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
