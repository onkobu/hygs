
.print create view distinct_capabilities

CREATE VIEW distinct_capabilities AS
    SELECT cap_name,
           cat_name
      FROM cap_capability cap
           JOIN
           cap_category cat ON cap.cap_category = cat.cat_id
     WHERE cap_id IN (
               SELECT MAX(cap_id) 
                 FROM cap_capability
                GROUP BY cap_name
           );

.print create view your_lack_of_skills

CREATE VIEW your_lack_of_skills AS
    SELECT *
      FROM cap_skill s
           JOIN
           cap_skill_mapping m ON s.skill_id = m.skill_id
           JOIN
           cap_capability c ON m.cap_id = c.cap_id;

.print create view your_skills

CREATE VIEW your_skills AS
    SELECT *
      FROM cap_skill s
           JOIN
           cap_skill_mapping m ON s.skill_id = m.skill_id
           JOIN
           cap_capability c ON m.cap_id = c.cap_id;

INSERT INTO app_capability ( cap_version, cap_description, cap_name )
    VALUES ( 8, 'add skill-views', 'skill views' );


