BEGIN TRANSACTION;

.print Insert categories

INSERT INTO cap_category (
                             cat_id,
                             cat_name
                         )
                         VALUES (
                             25,
                             'Datenmigration'
                         );

.print Insert capabilities

INSERT INTO cap_capability (
                               cap_category,
                               cap_name,
                               cap_id
                           )
                           VALUES (
                               17,
                               'Feign Clients',
                               101
                           ),
                           (
                               25,
                               'Flyway',
                               102
                           ),
                           (
                               1,
                               'Glassfish',
                               103
                           ),
                           (
                               1,
                               'Wildfly',
                               104
                           ),
                           (
                               1,
                               'Undertow',
                               105
                           );

INSERT INTO app_capability ( cap_version, cap_description, cap_name )
    VALUES ( 13, 'add middleware', 'undertow_cap_dml' );


COMMIT TRANSACTION;
