INSERT INTO cap_category (cat_id, cat_name ) VALUES 
	( 19, 'Design-Methode'),
    ( 18, 'Projektmanagement' );

INSERT INTO cap_capability (
                               cap_category,
                               cap_name,
                               cap_id
                           )
                           VALUES 
						(19, 'Clean Code', 81),
                        (19, 'Xtreme Programming', 82 ),
                        (19, 'Domänen-getriebene Entwicklung', 83 ),
                        (19, 'testgetriebene Entwicklung', 84 ),
                        (18, 'Kanban', 85 ),
                        (18, 'Wasserfall', 86 ),
                        (18, 'SCRUM', 87 ),
						(18, 'V-MODELL XT', 88);


INSERT INTO app_capability ( cap_version, cap_description, cap_name )
    VALUES ( 9, 'add capability data', 'capability dml 2' );


