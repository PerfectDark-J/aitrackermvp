BEGIN;

DROP TABLE IF EXISTS WorkLog;
DROP TABLE IF EXISTS Task;
DROP TABLE IF EXISTS Project;
DROP TABLE IF EXISTS Users;

CREATE TABLE Users (
userId serial PRIMARY KEY,
isManager boolean,
userEmail varchar(255),
isActivated boolean
);

CREATE TABLE Task (
taskid serial PRIMARY KEY,
tasktitle varchar(255),
taskdescription varchar(255),
taskiscompleted boolean,
taskcompletiondate varchar(255),
taskisrecurring boolean,
userId integer REFERENCES Users(userId)
);

CREATE TABLE log (
logid serial PRIMARY KEY,
content text,
type varchar(255),
date varchar(255)
);

COMMIT;