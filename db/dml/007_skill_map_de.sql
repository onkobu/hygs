--
-- File generated with SQLiteStudio v3.2.1 on So. März 17 15:08:16 2019
--
-- Text encoding used: UTF-8
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

.print inserting skill levels

INSERT INTO cap_skill (skill_id, skill_name, skill_description, skill_level) VALUES (1, 'Drittsprache', 'Sprache erlernt und länger nicht mehr gesprochen, nicht verhandlungssicher aber alltagstauglich', 1);
INSERT INTO cap_skill (skill_id, skill_name, skill_description, skill_level) VALUES (2, 'Schulwissen', 'Sprache wurde in der Schule gelernt, nicht regelmäßig gesprochen', 0);
INSERT INTO cap_skill (skill_id, skill_name, skill_description, skill_level) VALUES (3, 'Zweitsprache', 'Sprache wird regelmäßig gesprochen, verhandlungssicher', 2);
INSERT INTO cap_skill (skill_id, skill_name, skill_description, skill_level) VALUES (4, 'Muttersprache', 'Sprache wird fließend gesprochen', 3);
INSERT INTO cap_skill (skill_id, skill_name, skill_description, skill_level) VALUES (5, 'Trainer', 'Kenntnisse können anderen theoretisch und praktisch sicher vermittelt werden, rückfragenfrei', 3);
INSERT INTO cap_skill (skill_id, skill_name, skill_description, skill_level) VALUES (6, 'Expertenwissen', 'tiefgehende Kenntnisse, sicher in der Anwendung, Paradigmen und Theorie verstanden', 2);
INSERT INTO cap_skill (skill_id, skill_name, skill_description, skill_level) VALUES (7, 'Fachwissen', 'regelmäßig angewendet', 1);
INSERT INTO cap_skill (skill_id, skill_name, skill_description, skill_level) VALUES (8, 'Grundwissen', 'die Grundbegriffe sind bekannt und es wurde in begrenztem Umfang angewendet', 0);

INSERT INTO app_capability ( cap_version, cap_description, cap_name )
    VALUES ( 7, 'add skill data', 'skill dml' );



COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
