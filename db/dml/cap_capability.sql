--
-- File generated with SQLiteStudio v3.2.1 on Fr. März 15 21:13:14 2019
--
-- Text encoding used: UTF-8
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

.print populating cap_capability

INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (1, 'Subversion', 8, NULL);
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (2, 'Git', 8, '1.7');
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (3, 'Maven', 12, '3');
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (4, 'Maven', 12, '2');
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (5, 'Eclipse', 5, NULL);
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (6, 'Apache', 1, '2.4');
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (7, 'Tomcat', 1, '7');
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (8, 'Tomcat', 1, '6');
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (9, 'Java', 6, '8');
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (10, 'Java', 6, '7');
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (11, 'Java', 6, '6');
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (12, 'Java', 6, '1.5');
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (13, 'Java', 6, '1.4');
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (14, 'Java', 6, '1.3');
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (15, 'JBoss', 1, '6');
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (16, 'JBoss', 1, '5');
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (17, 'PostgreSQL', 3, '11');
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (18, 'PostgreSQL', 3, '10');
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (19, 'PostgreSQL', 3, '9');
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (20, 'PostgreSQL', 3, '8');
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (21, 'MySQL', 3, NULL);
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (22, 'SQLite', 3, '3.x');
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (23, 'Oracle', 3, '11');
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (24, 'Oracle', 3, '10');
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (25, 'Oracle', 3, '9');
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (26, 'Oracle', 3, '8');
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (27, 'Perl', 14, NULL);
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (28, 'Bash', 14, NULL);
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (29, 'PHP', 6, '7');
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (30, 'PHP', 6, '5');
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (31, 'Apache FOP', 10, NULL);
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (32, 'AngularJS', 15, '1.6');
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (33, 'Javascript', 14, NULL);
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (34, 'HTML', 13, '5');
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (35, 'HTML', 13, '4');
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (36, 'Swing', 16, NULL);
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (37, 'OrientDB', 3, NULL);
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (38, 'MongoDB', 3, '3.6');
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (39, 'JSP', 15, NULL);
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (40, 'Spring Security', 9, NULL);
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (41, 'Spring Data REST', 9, NULL);
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (42, 'Spring Data', 9, NULL);
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (43, 'Spring Boot', 9, NULL);
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (44, 'JSF', 15, NULL);
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (45, 'Servlet', 9, NULL);
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (46, 'ANTLR', 5, NULL);
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (47, 'Visual Paradigm', 4, NULL);
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (48, 'Sparx Enterprise Architect', 4, NULL);
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (49, 'Rational Rose', 4, NULL);
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (50, 'Telelogic CM Synergy', 8, NULL);
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (51, 'Netflix Stack', 9, NULL);
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (52, 'Protobuf', 17, NULL);
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (53, 'JAX-WS', 17, NULL);
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (54, 'JMeter', 11, NULL);
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (55, 'Mercurial Testrunner', 11, NULL);
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (56, 'Webdriver', 11, NULL);
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (57, 'Newman', 11, NULL);
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (58, 'Postman', 11, NULL);
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (59, 'Selenium', 11, NULL);
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (60, 'JUnit', 11, '5');
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (61, 'JUnit', 11, '4');
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (62, 'JUnit', 11, '3');
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (63, 'SOAP-UI', 11, NULL);
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (64, 'Oracle Forms', 15, NULL);
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (65, 'Bootstrap', 15, NULL);
INSERT INTO cap_capability (cap_id, cap_name, cap_category, cap_version) VALUES (66, 'Ant', 12, NULL);

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
