
CREATE DATABASE IF NOT EXISTS batch_employee;

USE batch_employee;

CREATE TABLE IF NOT EXISTS employee (
  id MEDIUMINT NOT NULL AUTO_INCREMENT,
  name varchar(255),
  department varchar(255),
  salary double,
primary key (id)
);

SET character_set_client = utf8;
SET character_set_connection = utf8;
SET character_set_results = utf8;
SET collation_connection = utf8_general_ci;