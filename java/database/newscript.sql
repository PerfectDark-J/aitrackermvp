BEGIN;

DROP TABLE IF EXISTS log;
DROP TABLE IF EXISTS task;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
userId serial PRIMARY KEY,
isManager boolean,
userEmail varchar(255),
isActivated boolean
);

CREATE TABLE task (
  taskid serial PRIMARY KEY,
  tasktitle varchar(255),
  taskdescription varchar(255),
  taskiscompleted boolean,
  taskcompletiondate varchar(255),
  taskisrecurring boolean,
  userid integer REFERENCES Users(userId)
);

CREATE TABLE log (
  id serial PRIMARY KEY,
  userid integer REFERENCES Users(userId),
  content text,
  title varchar(250),
  type varchar(255),
  date timestamp,
  description text,
  exercise varchar(255),
  reps integer,
  weight integer,
  minutes integer,
  title varchar(255),
  taskid integer
);

CREATE TABLE subtask (
  subtaskid SERIAL PRIMARY KEY,
  taskid INTEGER,
  subtitle VARCHAR(255),
  iscompleted BOOLEAN
);




COMMIT;