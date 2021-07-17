--
-- File generated with SQLiteStudio v3.2.1 on Sa. Juli 17 22:00:12 2021
--
-- Text encoding used: UTF-8
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

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

INSERT INTO app_capability (cap_id, cap_name, cap_description, cap_version, cap_timestamp) VALUES (1, 'init', 'initial ddl creating structure', 0, datetime('now','localtime'));

-- Table: app_maintenance
CREATE TABLE app_maintenance (
    mtc_id           INTEGER       PRIMARY KEY AUTOINCREMENT
                                   NOT NULL,
    mtc_action       VARCHAR (128) NOT NULL,
    mtc_element_type CHAR (16)     NOT NULL,
    mtc_element_name VARCHAR (255) NOT NULL
);


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

INSERT INTO app_properties (prop_id, prop_name, prop_val_text, prop_val_num, prop_val_date) VALUES (1, 'language', 'de', NULL, NULL);
INSERT INTO app_properties (prop_id, prop_name, prop_val_text, prop_val_num, prop_val_date) VALUES (2, 'last_backup', NULL, NULL, NULL);

-- Table: cap_skill_mapping
CREATE TABLE cap_skill_mapping (
    cap_id      INTEGER REFERENCES t_capability (cap_id),
    skill_id    INTEGER REFERENCES t_skill (skill_id),
    skill_since DATE    NOT NULL,
    UNIQUE (
        cap_id,
        skill_id,
        skill_since
    )
);


-- Table: log_audit
CREATE TABLE log_audit (
    aud_timestamp DATETIME      NOT NULL,
    aud_table     VARCHAR (128) NOT NULL,
    aud_action    CHAR (16)     NOT NULL,
    aud_id        INTEGER       PRIMARY KEY AUTOINCREMENT
                                NOT NULL,
    aud_ref       INTEGER       NOT NULL,
    aud_ref2      INTEGER
);

-- Table: prj_cap_mapping
CREATE TABLE prj_cap_mapping (
    prj_id          INTEGER REFERENCES t_project (prj_id),
    cap_id          INTEGER REFERENCES t_capability (cap_id),
    assigned_weight INTEGER CHECK (assigned_weight >= 0 AND 
                                   assigned_weight <= 100) 
                            NOT NULL
                            DEFAULT (100),
    UNIQUE (
        prj_id,
        cap_id
    )
);

-- Table: prj_role_mapping
CREATE TABLE prj_role_mapping (
    prj_id  INTEGER REFERENCES t_project (prj_id) 
                    NOT NULL,
    role_id INTEGER REFERENCES t_role (role_id) 
                    NOT NULL,
    UNIQUE (
        prj_id,
        role_id
    )
);

-- Table: t_capability
CREATE TABLE t_capability (
    cap_id          INTEGER      PRIMARY KEY AUTOINCREMENT
                                 NOT NULL,
    cap_name        VARCHAR (64) NOT NULL,
    cap_category_id INTEGER      REFERENCES t_category (cat_id),
    cap_version     CHAR (16),
    cap_deleted     BOOLEAN      DEFAULT FALSE,
    UNIQUE (
        cap_name,
        cap_category_id,
        cap_version
    )
);

-- Table: t_category
CREATE TABLE t_category (
    cat_name    VARCHAR (128) UNIQUE,
    cat_id      INTEGER       PRIMARY KEY AUTOINCREMENT
                              NOT NULL,
    cat_deleted BOOLEAN       DEFAULT FALSE
);

-- Table: t_company
CREATE TABLE t_company (
    cmp_name    VARCHAR (255) NOT NULL
                              UNIQUE,
    cmp_id      INTEGER       PRIMARY KEY AUTOINCREMENT,
    cmp_street  VARCHAR (128),
    cmp_city    VARCHAR (128),
    cmp_zip     CHAR (10),
    cmp_deleted BOOLEAN       DEFAULT FALSE
);

-- Table: t_project
CREATE TABLE t_project (
    prj_name        VARCHAR (64)  NOT NULL,
    prj_from        DATE          NOT NULL,
    prj_to          DATE,
    prj_company_id  INTEGER       REFERENCES t_company (cmp_id) 
                                  NOT NULL,
    prj_id          INTEGER       PRIMARY KEY AUTOINCREMENT
                                  NOT NULL,
    prj_description VARCHAR (255),
    prj_weight      INTEGER       CHECK (prj_weight >= 0 AND 
                                         prj_weight <= 100) 
                                  DEFAULT (100) 
                                  NOT NULL,
    prj_deleted     BOOLEAN       DEFAULT FALSE
);

-- Table: t_role
CREATE TABLE t_role (
    role_id      INTEGER      PRIMARY KEY AUTOINCREMENT
                              NOT NULL,
    role_name    VARCHAR (64) UNIQUE
                              NOT NULL,
    role_level   INTEGER      NOT NULL,
    role_deleted BOOLEAN      DEFAULT FALSE
);

-- Table: t_skill
CREATE TABLE t_skill (
    skill_id          INTEGER       PRIMARY KEY AUTOINCREMENT
                                    NOT NULL,
    skill_name        VARCHAR (64)  NOT NULL
                                    UNIQUE,
    skill_description VARCHAR (255),
    skill_level       INTEGER       NOT NULL,
    cap_deleted       BOOLEAN       DEFAULT FALSE
);


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


-- Trigger: cap_capability_audit_del
CREATE TRIGGER cap_capability_audit_del
         AFTER DELETE
            ON t_capability
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref
                          )
                          VALUES (
                              datetime('now'),
                              't_capability',
                              'DELETE',
                              old.cap_id
                          );
END;


-- Trigger: cap_capability_audit_ins
CREATE TRIGGER cap_capability_audit_ins
         AFTER INSERT
            ON t_capability
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref
                          )
                          VALUES (
                              datetime('now'),
                              't_capability',
                              'INSERT',
                              new.cap_id
                          );
END;


-- Trigger: cap_capability_audit_upd
CREATE TRIGGER cap_capability_audit_upd
         AFTER UPDATE
            ON t_capability
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref
                          )
                          VALUES (
                              datetime('now'),
                              't_capability',
                              'UPDATE',
                              new.cap_id
                          );
END;


-- Trigger: cap_category_audit_del
CREATE TRIGGER cap_category_audit_del
         AFTER DELETE
            ON t_category
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref
                          )
                          VALUES (
                              datetime('now'),
                              't_category',
                              'DELETE',
                              old.cat_id
                          );
END;


-- Trigger: cap_category_audit_ins
CREATE TRIGGER cap_category_audit_ins
         AFTER INSERT
            ON t_category
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref
                          )
                          VALUES (
                              datetime('now'),
                              't_category',
                              'INSERT',
                              new.cat_id
                          );
END;


-- Trigger: cap_category_audit_upd
CREATE TRIGGER cap_category_audit_upd
         AFTER UPDATE
            ON t_category
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref
                          )
                          VALUES (
                              datetime('now'),
                              't_category',
                              'UPDATE',
                              new.cat_id
                          );
END;


-- Trigger: cap_project_audit_del
CREATE TRIGGER cap_project_audit_del
         AFTER DELETE
            ON t_project
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref
                          )
                          VALUES (
                              datetime('now'),
                              't_project',
                              'DELETE',
                              old.prj_id
                          );
END;


-- Trigger: cap_project_audit_ins
CREATE TRIGGER cap_project_audit_ins
         AFTER INSERT
            ON t_project
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref
                          )
                          VALUES (
                              datetime('now'),
                              't_project',
                              'INSERT',
                              new.prj_id
                          );
END;


-- Trigger: cap_project_audit_upd
CREATE TRIGGER cap_project_audit_upd
         AFTER UPDATE
            ON t_project
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref
                          )
                          VALUES (
                              datetime('now'),
                              't_project',
                              'UPDATE',
                              new.prj_id
                          );
END;


-- Trigger: cap_skill_audit_del
CREATE TRIGGER cap_skill_audit_del
         AFTER DELETE
            ON t_skill
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref
                          )
                          VALUES (
                              datetime('now'),
                              't_skill',
                              'DELETE',
                              old.skill_id
                          );
END;


-- Trigger: cap_skill_audit_ins
CREATE TRIGGER cap_skill_audit_ins
         AFTER INSERT
            ON t_skill
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref
                          )
                          VALUES (
                              datetime('now'),
                              't_skill',
                              'INSERT',
                              new.skill_id
                          );
END;


-- Trigger: cap_skill_audit_upd
CREATE TRIGGER cap_skill_audit_upd
         AFTER UPDATE
            ON t_skill
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref
                          )
                          VALUES (
                              datetime('now'),
                              't_skill',
                              'UPDATE',
                              new.skill_id
                          );
END;


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


-- Trigger: prj_company_audit_del
CREATE TRIGGER prj_company_audit_del
         AFTER DELETE
            ON t_company
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
            ON t_company
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref
                          )
                          VALUES (
                              datetime('now'),
                              't_company',
                              'INSERT',
                              new.cmp_id
                          );
END;


-- Trigger: prj_company_audit_upd
CREATE TRIGGER prj_company_audit_upd
         AFTER UPDATE
            ON t_company
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref
                          )
                          VALUES (
                              datetime('now'),
                              't_company',
                              'UPDATE',
                              new.cmp_id
                          );
END;


-- Trigger: prj_role_audit_del
CREATE TRIGGER prj_role_audit_del
         AFTER DELETE
            ON t_role
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref
                          )
                          VALUES (
                              datetime('now'),
                              't_role',
                              'DELETE',
                              old.role_id
                          );
END;


-- Trigger: prj_role_audit_ins
CREATE TRIGGER prj_role_audit_ins
         AFTER INSERT
            ON t_role
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref
                          )
                          VALUES (
                              datetime('now'),
                              't_role',
                              'INSERT',
                              new.role_id
                          );
END;


-- Trigger: prj_role_audit_upd
CREATE TRIGGER prj_role_audit_upd
         AFTER UPDATE
            ON t_role
BEGIN
    INSERT INTO log_audit (
                              aud_timestamp,
                              aud_table,
                              aud_action,
                              aud_ref
                          )
                          VALUES (
                              datetime('now'),
                              't_role',
                              'UPDATE',
                              new.role_id
                          );
END;


-- Trigger: prj_role_mapping_audit_del
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


-- Trigger: prj_role_mapping_audit_ins
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


-- Trigger: prj_role_mapping_audit_upd
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


-- View: cap_months
CREATE VIEW v_cap_months AS
    SELECT p.prj_id,
           c.cap_id,
           prj_name,
           prj_months,
           cap_name,
           cap_version,
           assigned_weight,
           prj_months * assigned_weight / 100.0 AS cap_months
      FROM v_prj_months p
           LEFT OUTER JOIN
           prj_cap_mapping m ON p.prj_id = m.prj_id
           LEFT OUTER JOIN
           t_capability c ON c.cap_id = m.cap_id
     WHERE NOT cap_name IS NULL;


-- View: distinct_capabilities
CREATE VIEW v_distinct_capabilities AS
    SELECT cap_name,
           cat_name
      FROM t_capability cap
           JOIN
           t_category cat ON cap.cap_category_id = cat.cat_id
     WHERE cap_id IN (
               SELECT MAX(cap_id) 
                 FROM t_capability
                GROUP BY cap_name
           );


-- View: prj_months
CREATE VIEW v_prj_months AS
    SELECT prj_id,
           prj_name,
           CAST ( (JulianDay(prj_to) - JulianDay(prj_from) ) AS INTEGER) / 30 * prj_weight / 100 AS prj_months
      FROM t_project
     ORDER BY prj_from ASC;


-- View: v_capabilityinproject
CREATE VIEW v_capabilityinproject AS
    SELECT p.prj_id AS assc_project_id,
           c.cap_id AS assc_capability_id,
           cap_name AS assc_name,
           assigned_weight AS assc_weight,
           cap_version AS assc_version
      FROM t_project p
           JOIN
           prj_cap_mapping m ON p.prj_id = m.prj_id
           JOIN
           t_capability c ON m.cap_id = c.cap_id;


-- View: your_lack_of_skills
CREATE VIEW v_lack_of_skills AS
    SELECT *
      FROM t_capability c
     WHERE NOT c.cap_id IN (
               SELECT cap_id
                 FROM cap_skill_mapping
           );


-- View: your_roles
CREATE VIEW v_roles AS
    SELECT p.prj_id,
           prj_name,
           prj_from,
           prj_to,
           prj_company_id,
           r.role_id,
           r.role_name
      FROM t_project p
           JOIN
           prj_role_mapping m ON p.prj_id = m.prj_id
           JOIN
           t_role r ON m.role_id = r.role_id;


-- View: your_skills
CREATE VIEW v_skills AS
    SELECT *
      FROM t_skill s
           JOIN
           cap_skill_mapping m ON s.skill_id = m.skill_id
           JOIN
           t_capability c ON m.cap_id = c.cap_id;


COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
