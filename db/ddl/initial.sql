--
-- File generated with SQLiteStudio v3.2.1 on Fr. März 15 21:12:21 2019
--
-- Text encoding used: UTF-8
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

.print table app_capability

-- Table: app_capability
CREATE TABLE app_capability (
    cap_id          INTEGER       PRIMARY KEY AUTOINCREMENT,
    cap_name        VARCHAR (50)  NOT NULL,
    cap_description VARCHAR (255),
    cap_version     INTEGER       NOT NULL,
    cap_timestamp   DATETIME      DEFAULT (datetime('now', 'localtime') ) 
                                  NOT NULL,
    UNIQUE (
        cap_name,
        cap_version
    )
);

.print table app_maintenance

-- Table: app_maintenance
CREATE TABLE app_maintenance (
    mtc_id           INTEGER       PRIMARY KEY AUTOINCREMENT
                                   NOT NULL,
    mtc_action       VARCHAR (128) NOT NULL,
    mtc_element_type CHAR (16)     NOT NULL,
    mtc_element_name VARCHAR (255) NOT NULL
);

.print table cap_capability

-- Table: cap_capability
CREATE TABLE cap_capability (
    cap_id       INTEGER      PRIMARY KEY AUTOINCREMENT
                              NOT NULL,
    cap_name     VARCHAR (64) NOT NULL,
    cap_category INTEGER      REFERENCES cap_category (cat_id),
    cap_version  CHAR (16),
    UNIQUE (
        cap_name,
        cap_category,
        cap_version
    )
);

.print table cap_category

-- Table: cap_category
CREATE TABLE cap_category (
    cat_name VARCHAR (128) UNIQUE,
    cat_id   INTEGER       PRIMARY KEY AUTOINCREMENT
                           NOT NULL
);

.print table cap_project

-- Table: cap_project
CREATE TABLE cap_project (
    prj_name        VARCHAR (64)  NOT NULL,
    prj_from        DATE          NOT NULL,
    prj_to          DATE,
    prj_company     INTEGER       REFERENCES prj_company (cmp_id) 
                                  NOT NULL,
    prj_id          INTEGER       PRIMARY KEY AUTOINCREMENT
                                  NOT NULL,
    prj_description VARCHAR (255),
    prj_weight      INTEGER       CHECK (prj_weight >= 0 AND 
                                         prj_weight <= 100) 
                                  DEFAULT (100) 
                                  NOT NULL
);

.print table log_audit

-- Table: log_audit
CREATE TABLE log_audit (
    aud_timestamp DATETIME      NOT NULL,
    aud_table     VARCHAR (128) NOT NULL,
    aud_action    CHAR (16)     NOT NULL,
    aud_id        INTEGER       PRIMARY KEY AUTOINCREMENT
                                NOT NULL,
    aud_ref       INTEGER       NOT NULL
);

.print table prj_cap_mapping

-- Table: prj_cap_mapping
CREATE TABLE prj_cap_mapping (
    prj_id          INTEGER REFERENCES cap_project (prj_id),
    cap_id          INTEGER REFERENCES cap_capability (cap_id),
    assigned_weight INTEGER CHECK (assigned_weight >= 0 AND 
                                   assigned_weight <= 100) 
                            NOT NULL
                            DEFAULT (100),
    UNIQUE (
        prj_id,
        cap_id
    )
);

.print table prj_company

-- Table: prj_company
CREATE TABLE prj_company (
    cmp_name   VARCHAR (255) NOT NULL
                             UNIQUE,
    cmp_id     INTEGER       PRIMARY KEY AUTOINCREMENT,
    cmp_street VARCHAR (128),
    cmp_city   VARCHAR (128),
    cmp_zip    CHAR (10) 
);

.print creating triggers

-- Trigger: app_capability_audit_del
CREATE TRIGGER app_capability_audit_del
         AFTER DELETE
            ON app_capability
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref
                          )
                          VALUES (
                              datetime('now'),
                              'app_capability',
                              'DELETE',
                              old.cap_id
                          );
END;


-- Trigger: app_capability_audit_ins
CREATE TRIGGER app_capability_audit_ins
         AFTER INSERT
            ON app_capability
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref
                          )
                          VALUES (
                              datetime('now'),
                              'app_capability',
                              'INSERT',
                              new.cap_id
                          );
END;


-- Trigger: app_capability_audit_upd
CREATE TRIGGER app_capability_audit_upd
         AFTER UPDATE
            ON app_capability
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref
                          )
                          VALUES (
                              datetime('now'),
                              'app_capability',
                              'UPDATE',
                              new.cap_id
                          );
END;


-- Trigger: app_maintenance_audit_del
CREATE TRIGGER app_maintenance_audit_del
         AFTER DELETE
            ON app_maintenance
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref
                          )
                          VALUES (
                              datetime('now'),
                              'app_maintenance',
                              'DELETE',
                              old.mtc_id
                          );
END;


-- Trigger: app_maintenance_audit_ins
CREATE TRIGGER app_maintenance_audit_ins
         AFTER INSERT
            ON app_maintenance
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref
                          )
                          VALUES (
                              datetime('now'),
                              'app_maintenance',
                              'INSERT',
                              new.mtc_id
                          );
END;


-- Trigger: app_maintenance_audit_upd
CREATE TRIGGER app_maintenance_audit_upd
         AFTER UPDATE
            ON app_maintenance
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref
                          )
                          VALUES (
                              datetime('now'),
                              'app_maintenance',
                              'UPDATE',
                              new.mtc_id
                          );
END;


-- Trigger: cap_capability_audit_del
CREATE TRIGGER cap_capability_audit_del
         AFTER DELETE
            ON cap_capability
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref
                          )
                          VALUES (
                              datetime('now'),
                              'cap_capability',
                              'DELETE',
                              old.cap_id
                          );
END;


-- Trigger: cap_capability_audit_ins
CREATE TRIGGER cap_capability_audit_ins
         AFTER INSERT
            ON cap_capability
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref
                          )
                          VALUES (
                              datetime('now'),
                              'cap_capability',
                              'INSERT',
                              new.cap_id
                          );
END;


-- Trigger: cap_capability_audit_upd
CREATE TRIGGER cap_capability_audit_upd
         AFTER UPDATE
            ON cap_capability
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref
                          )
                          VALUES (
                              datetime('now'),
                              'cap_capability',
                              'UPDATE',
                              new.cap_id
                          );
END;


-- Trigger: cap_category_audit_del
CREATE TRIGGER cap_category_audit_del
         AFTER DELETE
            ON cap_category
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref
                          )
                          VALUES (
                              datetime('now'),
                              'cap_category',
                              'DELETE',
                              old.cat_id
                          );
END;


-- Trigger: cap_category_audit_ins
CREATE TRIGGER cap_category_audit_ins
         AFTER INSERT
            ON cap_category
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref
                          )
                          VALUES (
                              datetime('now'),
                              'cap_category',
                              'INSERT',
                              new.cat_id
                          );
END;


-- Trigger: cap_category_audit_upd
CREATE TRIGGER cap_category_audit_upd
         AFTER UPDATE
            ON cap_category
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref
                          )
                          VALUES (
                              datetime('now'),
                              'cap_category',
                              'UPDATE',
                              new.cat_id
                          );
END;


-- Trigger: cap_project_audit_del
CREATE TRIGGER cap_project_audit_del
         AFTER DELETE
            ON cap_project
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref
                          )
                          VALUES (
                              datetime('now'),
                              'cap_project',
                              'DELETE',
                              old.prj_id
                          );
END;


-- Trigger: cap_project_audit_ins
CREATE TRIGGER cap_project_audit_ins
         AFTER INSERT
            ON cap_project
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref
                          )
                          VALUES (
                              datetime('now'),
                              'cap_project',
                              'INSERT',
                              new.prj_id
                          );
END;


-- Trigger: cap_project_audit_upd
CREATE TRIGGER cap_project_audit_upd
         AFTER UPDATE
            ON cap_project
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref
                          )
                          VALUES (
                              datetime('now'),
                              'cap_project',
                              'UPDATE',
                              new.prj_id
                          );
END;


-- Trigger: prj_company_audit_del
CREATE TRIGGER prj_company_audit_del
         AFTER DELETE
            ON prj_company
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref
                          )
                          VALUES (
                              datetime('now'),
                              'prj_company',
                              'DELETE',
                              old.cmp_id
                          );
END;


-- Trigger: prj_company_audit_ins
CREATE TRIGGER prj_company_audit_ins
         AFTER INSERT
            ON prj_company
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref
                          )
                          VALUES (
                              datetime('now'),
                              'prj_company',
                              'INSERT',
                              new.cmp_id
                          );
END;


-- Trigger: prj_company_audit_upd
CREATE TRIGGER prj_company_audit_upd
         AFTER UPDATE
            ON prj_company
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref
                          )
                          VALUES (
                              datetime('now'),
                              'prj_company',
                              'UPDATE',
                              new.cmp_id
                          );
END;

.print view cap_months

-- View: cap_months
CREATE VIEW cap_months AS
    SELECT p.prj_id,
           c.cap_id,
           prj_name,
           prj_months,
           cap_name,
           cap_version,
           assigned_weight,
           prj_months * assigned_weight / 100.0 AS cap_months
      FROM prj_months p
           LEFT OUTER JOIN
           prj_cap_mapping m ON p.prj_id = m.prj_id
           LEFT OUTER JOIN
           cap_capability c ON c.cap_id = m.cap_id
     WHERE NOT cap_name IS NULL;

.print view prj_months

-- View: prj_months
CREATE VIEW prj_months AS
    SELECT prj_id,
           prj_name,
           CAST ( (JulianDay(prj_to) - JulianDay(prj_from) ) AS INTEGER) / 30 * prj_weight / 100 AS prj_months
      FROM cap_project
     ORDER BY prj_from ASC;


INSERT INTO app_capability ( cap_version, cap_description, cap_name )
    VALUES ( 0, 'initial ddl creating structure', 'init' );

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
