INSERT INTO roles VALUES (DEFAULT, 'Admin'),
  (DEFAULT, 'Project manager'),
  (DEFAULT, 'Employee'),
  (DEFAULT, 'Customer');

INSERT INTO priority VALUES (DEFAULT, 'Low'),
  (DEFAULT, 'Medium'),
  (DEFAULT, 'High'),
  (DEFAULT, 'Critical');


INSERT INTO company (id, name, description) VALUES (DEFAULT,'Звезда смерти','Top secret');


INSERT INTO users VALUES
  (DEFAULT, 'Дарт','Ситиус', '$2a$12$B9j8b1edtSfgFQ6Hageb9eNXCGy83uRwOR5HgX634B.W1bF5zUnIi', 1,1, 'sithTheBest@gmai.com','0345342123','1964-06-30',1),
  (DEFAULT, 'Дарт','Вейдер', '$2a$12$B9j8b1edtSfgFQ6Hageb9eNXCGy83uRwOR5HgX634B.W1bF5zUnIi', 2,1, 'sithTheBest1@gmai.com','0945334568','1965-01-30',1),
  (DEFAULT, 'Дарт','Молл', '$2a$12$B9j8b1edtSfgFQ6Hageb9eNXCGy83uRwOR5HgX634B.W1bF5zUnIi', 2,1, 'sithTheBest2@gmai.com','0944563568','1970-05-30',1),
  (DEFAULT, 'Штурмовик','Храбрый', '$2a$12$B9j8b1edtSfgFQ6Hageb9eNXCGy83uRwOR5HgX634B.W1bF5zUnIi', 3,1, 'shturm@gmai.com','0944534568','1990-02-15',1),
  (DEFAULT, 'Штурмовик','Петро', '$2a$12$B9j8b1edtSfgFQ6Hageb9eNXCGy83uRwOR5HgX634B.W1bF5zUnIi', 3,1, 'shturm1@gmai.com','0944234568','1995-01-15',1),
  (DEFAULT, 'Джаба','Хатт', '$2a$12$B9j8b1edtSfgFQ6Hageb9eNXCGy83uRwOR5HgX634B.W1bF5zUnIi', 3,1, 'bjaba@gmai.com','0944234568','1965-01-23',2),
  (DEFAULT, 'Генералл','Гривус', '$2a$12$B9j8b1edtSfgFQ6Hageb9eNXCGy83uRwOR5HgX634B.W1bF5zUnIi', 3,1, 'general@gmai.com','0944234568','1980-06-23',1),
  (DEFAULT, 'Бoба','Фетт', '$2a$12$B9j8b1edtSfgFQ6Hageb9eNXCGy83uRwOR5HgX634B.W1bF5zUnIi', 3,1, 'fett@gmai.com','0944234568','1985-06-20',1),
  (DEFAULT, 'Биба','Фетт', '$2a$12$B9j8b1edtSfgFQ6Hageb9eNXCGy83uRwOR5HgX634B.W1bF5zUnIi', 3,1, 'fett1@gmai.com','0944234568','1984-06-20',1),
  (DEFAULT, 'Джастин','Бибер','$2a$12$B9j8b1edtSfgFQ6Hageb9eNXCGy83uRwOR5HgX634B.W1bF5zUnIi', 3,1, 'biber@gmai.com','0934336883','1996-06-20',1);

INSERT INTO project VALUES
  (DEFAULT, 'Разрушить планету','Обрушить гнев ситов на невинных людей', 1,NULL, NULL,'2019-07-20',2, DEFAULT),
  (DEFAULT, 'Световые мечи','Создать новые красивые световые мечи', 1,NULL, NULL,'2018-07-20',2, DEFAULT),
  (DEFAULT, 'Стать депутатом','Стать депутатом ВР Украины', 1,NULL, NULL,'2018-03-20',2, DEFAULT),

  (DEFAULT, 'Убить джедая','Найти джедая и сразиться с ним и победить', 1, NULL, NULL,'2018-06-30',3, DEFAULT),
  (DEFAULT, 'Разведать планы повстанцев','Втерется в доверие к повстанцу Акакию', 1, NULL, NULL,'2017-04-03',3, DEFAULT);

INSERT INTO customer VALUES
  (DEFAULT, 'Biba Company', NULL ,9,1),
  (DEFAULT, 'Biber Company', NULL ,10,4);

INSERT INTO sprint VALUES
  (DEFAULT,'Начало проекта',1,'Описание, найти подрядчиков, итд',NULL,NULL,'2018-07-30', NULL, FALSE, FALSE ),
  (DEFAULT,'Оброботка',1,'Найти робочих, подготовка к разрушению',NULL,NULL,'2018-11-30', 1, FALSE, FALSE ),

  (DEFAULT,'Начало проекта',2,'Найти робочих, придумать дизайн',NULL,NULL,'2017-12-30', NULL, FALSE, FALSE ),
  (DEFAULT,'Обработка',2,'Найти робочих, утвердить дизайн итд',NULL,NULL,'2018-01-30', 1, FALSE, FALSE ),

  (DEFAULT,'Начало проекта',3,'Накопить взнос, купить костюм',NULL,NULL,'2017-04-04', NULL, FALSE, FALSE ),
  (DEFAULT,'Обработка',3,'Запустить медиа кампанию итд',NULL,NULL,'2017-07-04', 1, FALSE, FALSE ),

  (DEFAULT,'Начало проекта',4,'Привести себя в формуб записаться в спорт зал',NULL,NULL,'2017-04-04', NULL, FALSE, FALSE ),
  (DEFAULT,'Обработка',4,'Найти слабенького джедая',NULL,NULL,'2017-07-04', 1,FALSE, FALSE );

INSERT INTO  task(id, name, sprint_id,description,priority, plan_finish_date, estimate) VALUES
  (DEFAULT,'Составить ТЗ',1,'Написать техническоє задание, проконсультироваться с заказчиком', 1 ,'2018-01-02',4),
  (DEFAULT,'Составить смета',1,'Прописать все затраты', 2 ,'2018-02-01',10),
  (DEFAULT,'Партнеры и спонсоры',1,'Найти партнеров и спонсоров', 2 ,'2018-02-07',8),
  (DEFAULT,'Составить договор и акт',1,'Составить договор с будущими спонсорами', 2 ,'2018-02-01',7),
  (DEFAULT,'Званый ужин',1,'Провести званый ужин с партнерами и спонсорами', 3 ,'2018-01-29',10),

  (DEFAULT,'ТЗ',2,'Написать техническоє задание', 1 ,'2018-03-25',9),
  (DEFAULT,'Чертежи',2,'Чертежи для нового смертельного оружия', 3 ,'2018-06-01',10),
  (DEFAULT,'Уборка',2,'Уборка на Звезде Смерти', 3 ,'2018-08-01',3),
  (DEFAULT,'Работники',2,'Найти штуромвиков уборщиков', 3 ,'2018-06-01',4),
  (DEFAULT,'Репитиция',2,'Генеральная репетиция разрушения', 2 ,'2018-09-01',10);

INSERT INTO task_dependency VALUES
  (3,4),
  (3,5),
  (8,9);

INSERT INTO user_task VALUES
  (4,1),
  (5,2),
  (6,2),
  (7,2),
  (4,3),
  (8,4),
  (9,4),
  (7,5),
  (10,6),
  (9,7);