INSERT INTO roles VALUES (DEFAULT, 'Admin'),
  (DEFAULT, 'Project manager'),
  (DEFAULT, 'Employee'),
  (DEFAULT, 'Customer');

INSERT INTO priority VALUES (DEFAULT, 'Low'),
  (DEFAULT, 'Medium'),
  (DEFAULT, 'High'),
  (DEFAULT, 'Critical');


INSERT INTO company (id, name, description) VALUES (DEFAULT,'The Death Star','top secret'),
  (DEFAULT,'Google','google');


INSERT INTO users VALUES
  (DEFAULT, 'Dart','Veider', '$2a$12$B9j8b1edtSfgFQ6Hageb9eNXCGy83uRwOR5HgX634B.W1bF5zUnIi', 1,1, 'djedaiLohi@gmai.com','0345342123','1965-06-30',1),
  (DEFAULT, 'Nerzul','Petrovich', '$2a$04$EtIbZmy91dM/zu5nhUgIwuHGUkGxf/8ekLhA7pPsIkU6U9JUyBw2a', 3,1, 'zzaNerzula@gmai.com','0345342123','1995-06-30',1),
  (DEFAULT, 'Golum','Sudorenko', '$2a$12$3Br9vQoIaviqPAnkdkaUXuO6O/BF7uyJOiURjS1WAIakidrseY3TC', 2,1, 'moyaPrelist@gmai.com','0345342123','1985-06-30',1),
  (DEFAULT, 'Sergey','Brin', '$2a$12$GvkF6THOdwray/u0OdPyuOGjp4uY4GwyxIvoZh65H3YG1Zt.Sgz4.', 1,2, 'king@gmai.com','0345342123','1945-06-30',1),
  (DEFAULT, 'Stiv','Djops', '$2a$12$f3nuzjf0gZpgxVl4./45/eWzNzJdton7w5zW6gcYjDBC92lA9zKA.', 2,2, 'king1@gmai.com','0345342123','1945-06-30',1),
  (DEFAULT, 'Petro','Petro', '$2a$12$f3nuzjf0gZpgxVl4./45/eWzNzJdton7w5zW6gcYjDBC92lA9zKA.', 4,null, 'petro@gmai.com','0345342123','1945-06-30',1),
  (DEFAULT, 'Fedir','Fedir', '$2a$12$f3nuzjf0gZpgxVl4./45/eWzNzJdton7w5zW6gcYjDBC92lA9zKA.', 4,null, 'Fedir@gmai.com','0345342123','1945-06-30',1);

INSERT INTO project VALUES
  (DEFAULT, 'destroy','destroy peaceful planet', 1, current_date, NULL, '2018-06-30',3 , DEFAULT),
  (DEFAULT, 'Project','Project',2,  '1995-06-30',NULL,'2017-06-30',5, TRUE),
  (DEFAULT, 'some','i dont know', 1, current_date,NULL ,'2018-06-30',NULL , DEFAULT);

INSERT INTO customer VALUES
  (DEFAULT, 'Petro Company', NULL ,6,1),
  (DEFAULT, 'Fedir Company', NULL ,7,2);

INSERT INTO sprint VALUES
  (DEFAULT,'start',1,null,'1995-06-30',NULL,'2017-06-30', null, TRUE , FALSE ),
  (DEFAULT,'next',1,null,'2017-06-30',NULL,'2017-11-30', 1, FALSE , FALSE ),
  (DEFAULT,'start',2,null,'2017-06-30',NULL,'2017-11-30', null, FALSE , FALSE );

INSERT INTO  task(id, name, sprint_id,description,priority, estimate) VALUES
  (DEFAULT,'first Task',1,null, DEFAULT ,2),
  (DEFAULT,'second Task',1,null,2,3),
  (DEFAULT,'third Task',1,null,3,3),
  (DEFAULT,'first Task',2,null,DEFAULT ,2);

INSERT INTO task_dependency VALUES
  (3,1),
  (3,2);

INSERT INTO user_task VALUES
  (1,1),
  (1,2),
  (2,1);