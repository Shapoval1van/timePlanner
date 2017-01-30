DROP TABLE IF EXISTS company CASCADE;
CREATE TABLE IF NOT EXISTS company(
  id SERIAL PRIMARY KEY,
  name VARCHAR(45) NOT NULL UNIQUE,
  date_creation DATE DEFAULT current_date,
  description TEXT
);


DROP TABLE IF EXISTS customer CASCADE;
CREATE TABLE IF NOT EXISTS customer(
  id SERIAL PRIMARY KEY,
  company_name VARCHAR(45) NOT NULL,
  description TEXT,
  user_id INTEGER,
  project_id INTEGER
);


DROP TABLE IF EXISTS project CASCADE;
CREATE TABLE IF NOT EXISTS project(
  id SERIAL PRIMARY KEY,
  name VARCHAR(45) NOT NULL,
  description TEXT,
  company_id INTEGER NOT NULL,
  start_date DATE,
  finish_date DATE,
  plan_finish_date DATE NOT NULL,
  project_manager_id INTEGER,
  is_started BOOLEAN DEFAULT FALSE,
  is_finished BOOLEAN DEFAULT FALSE
);


DROP TABLE IF EXISTS sprint CASCADE;
CREATE TABLE IF NOT EXISTS sprint (
  id SERIAL PRIMARY KEY,
  name VARCHAR(45) NOT NULL,
  projectId INTEGER NOT NULL,
  description TEXT,
  start_date DATE,
  finish_date DATE,
  plan_finish_date DATE NOT NULL,
  dependent_on INTEGER,
  is_started BOOLEAN DEFAULT FALSE,
  is_finished BOOLEAN DEFAULT FALSE
);


DROP TABLE IF EXISTS task CASCADE;
CREATE TABLE IF NOT EXISTS task(
  id SERIAL PRIMARY KEY,
  name VARCHAR(45) NOT NULL,
  sprint_id INTEGER NOT NULL,
  start_date DATE,
  finish_date DATE,
  is_started BOOLEAN DEFAULT FALSE,
  is_finished BOOLEAN DEFAULT FALSE,
  plan_finish_date DATE NOT NULL,
  description TEXT,
  priority INTEGER DEFAULT 1,
  estimate FLOAT
);


DROP TABLE IF EXISTS task_dependency CASCADE;
CREATE TABLE IF NOT EXISTS task_dependency(
  task_id INTEGER NOT NULL,
  depended_task_id INTEGER NOT NULL
);

DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE IF NOT EXISTS users(
  id SERIAL PRIMARY KEY,
  f_name VARCHAR(45) NOT NULL,
  l_name VARCHAR(45) NOT NULL,
  password VARCHAR(60) NOT NULL,
  roleId INTEGER NOT NULL,
  company_id INTEGER,
  email VARCHAR(45) NOT NULL  UNIQUE,
  phone VARCHAR(45),
  birth_date DATE,
  sex INTEGER CHECK (sex in (0,1,2))
);

DROP TABLE IF EXISTS roles CASCADE;
CREATE TABLE IF NOT EXISTS roles(
  id SERIAL PRIMARY KEY,
  role VARCHAR(30) NOT NULL
);

DROP TABLE IF EXISTS user_task CASCADE;

CREATE TABLE IF NOT EXISTS user_task(
  user_id INTEGER,
  task_id INTEGER
);

DROP TABLE IF EXISTS  priority CASCADE;
CREATE TABLE IF NOT EXISTS priority(
  id SERIAL PRIMARY KEY,
  priority VARCHAR(20)
);


ALTER TABLE customer ADD CONSTRAINT userId_fk FOREIGN KEY (user_id) REFERENCES users;
ALTER TABLE customer ADD CONSTRAINT projectId_fk FOREIGN KEY (project_id) REFERENCES project;

ALTER TABLE project ADD CONSTRAINT companyId_fk FOREIGN KEY (company_id) REFERENCES company;
ALTER TABLE project ADD CONSTRAINT pmId_fk FOREIGN KEY (project_manager_id) REFERENCES users;


ALTER TABLE sprint ADD CONSTRAINT projectId_fk FOREIGN KEY (projectId) REFERENCES project;
ALTER TABLE sprint ADD CONSTRAINT dependedOn_fk FOREIGN KEY (dependent_on) REFERENCES sprint;

ALTER TABLE task ADD CONSTRAINT sprint_id_fk FOREIGN KEY (sprint_id) REFERENCES sprint;
ALTER TABLE task ADD CONSTRAINT priority FOREIGN KEY (priority) REFERENCES priority;


ALTER TABLE task_dependency ADD CONSTRAINT Id1_fk FOREIGN KEY (task_id) REFERENCES task;
ALTER TABLE task_dependency ADD CONSTRAINT Id2_fk FOREIGN KEY (depended_task_id) REFERENCES task;
ALTER TABLE task_dependency ADD CONSTRAINT pk_task_dependency PRIMARY KEY(task_id, depended_task_id);

ALTER TABLE users ADD CONSTRAINT roleId_Fk FOREIGN KEY (roleId) REFERENCES roles;
ALTER TABLE users ADD CONSTRAINT companyId_fk FOREIGN KEY (company_id) REFERENCES company;

ALTER TABLE user_task  ADD CONSTRAINT userId_fk FOREIGN KEY (user_id) REFERENCES users;
ALTER TABLE user_task  ADD CONSTRAINT task_id_fk FOREIGN KEY (task_id) REFERENCES task;
ALTER TABLE user_task  ADD CONSTRAINT pk_userToTask PRIMARY KEY(user_id, task_id);