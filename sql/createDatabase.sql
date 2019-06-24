CREATE DATABASE company_simulator CHARACTER SET utf8;

CREATE TABLE users (
user_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(30) NOT NULL, 
password VARCHAR(30) NOT NULL);

CREATE TABLE companies (
company_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(30) NOT NULL,
cash INT NOT NULL,
owner_id INT NOT NULL,
FOREIGN KEY(owner_id) REFERENCES users(user_id) ON DELETE CASCADE);

CREATE TABLE employees (
employee_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(30) NOT NULL,
age INT NOT NULL,
salary INT NOT NULL,
performance INT NOT NULL,
description CHAR(255),
company_id INT NOT NULL,
FOREIGN KEY(company_id) REFERENCES companies(company_id) ON DELETE CASCADE);

ALTER TABLE employees ADD COLUMN sex CHAR(6) AFTER age;

CREATE TABLE contracts (
contract_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(30) NOT NULL,
performance_units INT NOT NULL,
fee INT NOT NULL,
deadline INT NOT NULL,
description CHAR(255),
company_id INT NOT NULL,
FOREIGN KEY(company_id) REFERENCES companies(company_id) ON DELETE CASCADE);

CREATE TABLE work_positions (
position_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
employee_id INT NOT NULL,
contract_id INT NOT NULL,
FOREIGN KEY(employee_id) REFERENCES employees(employee_id) ON DELETE CASCADE,
FOREIGN KEY(contract_id) REFERENCES contracts(contract_id) ON DELETE CASCADE);

ALTER TABLE contracts ADD COLUMN progress INT AFTER deadline;

