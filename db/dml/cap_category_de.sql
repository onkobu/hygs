--
-- File generated with SQLiteStudio v3.2.1 on Fr. März 15 21:13:33 2019
--
-- Text encoding used: UTF-8
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

.print populating cap_category, de

INSERT INTO cap_category (cat_name, cat_id) VALUES ('Middleware', 1);
INSERT INTO cap_category (cat_name, cat_id) VALUES ('Plattform', 2);
INSERT INTO cap_category (cat_name, cat_id) VALUES ('Datenbank', 3);
INSERT INTO cap_category (cat_name, cat_id) VALUES ('Entwurfswerkzeug', 4);
INSERT INTO cap_category (cat_name, cat_id) VALUES ('Entwicklungswerkzeug', 5);
INSERT INTO cap_category (cat_name, cat_id) VALUES ('Programmiersprache', 6);
INSERT INTO cap_category (cat_name, cat_id) VALUES ('Sprache', 7);
INSERT INTO cap_category (cat_name, cat_id) VALUES ('Versionskontrolle', 8);
INSERT INTO cap_category (cat_name, cat_id) VALUES ('Framework', 9);
INSERT INTO cap_category (cat_name, cat_id) VALUES ('Bibliothek', 10);
INSERT INTO cap_category (cat_name, cat_id) VALUES ('Testwerkzeug', 11);
INSERT INTO cap_category (cat_name, cat_id) VALUES ('Build-Werkzeug', 12);
INSERT INTO cap_category (cat_name, cat_id) VALUES ('Auszeichnungssprache', 13);
INSERT INTO cap_category (cat_name, cat_id) VALUES ('Skripting', 14);
INSERT INTO cap_category (cat_name, cat_id) VALUES ('Framework Web UI', 15);
INSERT INTO cap_category (cat_name, cat_id) VALUES ('Framework UI', 16);
INSERT INTO cap_category (cat_name, cat_id) VALUES ('Framework Kommunikation', 17);

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
