BEGIN TRANSACTION;

.print inserting additional categories

INSERT INTO cap_category (
                             cat_id,
                             cat_name
                         )
                         VALUES (
                             24,
                             'Architekturmodell'
                         ),
						(
                             23,
                             'Business Process Modelling'
                         ),
                         (
                             22,
                             'Visualisierung'
                         ),
                         (
                             21,
                             'Logging'
                         ),
                         (
                             20,
                             'Systemanalyse'
                         );


.print inserting additional roles

INSERT INTO prj_role (
                         role_level,
                         role_name,
                         role_id
                     )
                     VALUES (
                         1,
                         'Database Administrator',
                         11
                     ),
                     (
                         1,
                         'Auditor',
                         12
                     ),
                     (
                         2,
                         'Security Auditor',
                         13
                     ),
                     (
                         0,
                         'Reviewer',
                         14
                     ),
                     (
                         2,
                         'Performance Auditor',
                         15
                     ),
                     (
                         1,
                         'SCRUM Master',
                         16
                     );

.print insert additional capabilities

INSERT INTO cap_capability (
                               cap_category,
                               cap_name,
                               cap_id
                           )
                           VALUES (
                               12,
                               'Gradle',
                               97
                           ),
                           (
                               1,
                               'WebLogic',
                               96
                           ),
                           (
                               23,
                               'Flowable',
                               95
                           ),
                           (
                               23,
                               'Activity',
                               94
                           ),
                           (
                               20,
                               'Nagios',
                               93
                           ),
                           (
                               22,
                               'Kibana',
                               92
                           ),
                           (
                               21,
                               'Logstash',
                               91
                           ),
                           (
                               21,
                               'Elasticsearch',
                               90
                           ),
                           (
                               20,
                               'ELK-Stack',
                               89
                           ),
                           (
                               19,
                               'Model-Driven-Design',
                               98
                           ),
                           (
                               24,
                               'Service-orientierte Architektur',
                               99
                           ),
                           (
                               1,
                               'Enterprise Service Bus',
                               100
                           );
	;

INSERT INTO app_capability ( cap_version, cap_description, cap_name )
    VALUES ( 12, 'add application properties', 'roles and capabilities dml' );


COMMIT TRANSACTION;
