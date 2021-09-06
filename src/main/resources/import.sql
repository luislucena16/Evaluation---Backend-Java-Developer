INSERT INTO regions (id,name) VALUES (1, "South America");
INSERT INTO regions (id,name) VALUES (2, "Central America");
INSERT INTO regions (id,name) VALUES (3, "North America");
INSERT INTO regions (id,name) VALUES (4, "Europa");
INSERT INTO regions (id,name) VALUES (5, "Asia");
INSERT INTO regions (id,name) VALUES (6, "Africa");
INSERT INTO regions (id,name) VALUES (7, "Oceania");
INSERT INTO regions (id,name) VALUES (8, "Antarctica");
INSERT INTO customers (region_id,name,lastname,email,create_at) VALUES (1,'Luis','Lucena','luis@lucena.com','2021-05-21');
INSERT INTO customers (region_id,name,lastname,email,create_at) VALUES (1,'Jacinto','Rivera','jacirivera@lucena.com','2021-05-21');
INSERT INTO customers (region_id,name,lastname,email,create_at) VALUES (4,'Pedro','Mendez','pedroM@lucena.com','2021-05-21');
INSERT INTO customers (region_id,name,lastname,email,create_at) VALUES (5,'Samanta','Gonzales','samantaGon@lucena.com','2021-05-21');
INSERT INTO customers (region_id,name,lastname,email,create_at) VALUES (7,'Camila','Arrico','CamilaArri@lucena.com','2021-05-21');
INSERT INTO customers (region_id,name,lastname,email,create_at) VALUES (8,'Cecilia','Abreu','CeciAb@lucena.com','2021-05-21');
INSERT INTO customers (region_id,name,lastname,email,create_at) VALUES (3,'Santiago','Chalela','SasCha@lucena.com','2021-05-21');

INSERT INTO `users` (username,password,enabled,name,lastname,email) VALUES ('luis','$2a$10$3Btfog4MBQuoT3Kss6foZOleo2RwN6x5zTuNaM9bQZ.Tf83hDbFdi',1,'Alejandro','Lucena','luis@hotmail.com');
INSERT INTO `users` (username,password,enabled,name,lastname,email) VALUES ('admin','$2a$10$l4WOc6korA4a/xiL9TXBOuZdHGrtZUpNOHCxC9u6yfwH4JCmR43cC',1,'annon','Wesley','wesley@hotmail.com');

INSERT INTO `roles` (name) VALUES ('ROLE_USER');
INSERT INTO `roles` (name) VALUES ('ROLE_ADMIN');

INSERT INTO `users_roles` (users_id, role_id) VALUES (1,1);
INSERT INTO `users_roles` (users_id, role_id) VALUES (2,2);
INSERT INTO `users_roles` (users_id, role_id) VALUES (2,1);
 