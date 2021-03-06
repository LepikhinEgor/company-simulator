CREATE DATABASE company_simulator CHARACTER SET utf8;

CREATE TABLE users (
user_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(30) NOT NULL, 
password VARCHAR(30) NOT NULL);

ALTER TABLE users ADD COLUMN email VARCHAR(6) NOT NULL;
ALTER TABLE users CHANGE COLUMN `name` `login` VARCHAR(30) NOT NULL;
ALTER TABLE users CHANGE COLUMN `email` `email` VARCHAR(50) NOT NULL;

CREATE TABLE companies (
company_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(30) NOT NULL,
cash INT NOT NULL,
owner_id INT NOT NULL,
FOREIGN KEY(owner_id) REFERENCES users(user_id) ON DELETE CASCADE);

ALTER TABLE companies ADD COLUMN cash_update_time TIMESTAMP;

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

ALTER TABLE contracts DROP COLUMN `start_date`;
ALTER TABLE contracts ADD COLUMN start_date TIMESTAMP NOT NULL AFTER fee;
ALTER TABLE contracts ADD COLUMN last_progress INT NOT NULL AFTER deadline;

ALTER TABLE contracts CHANGE COLUMN `start_date` `team_changed_date` TIMESTAMP NOT NULL;
ALTER TABLE contracts CHANGE COLUMN `team_changed_date` `team_changed_date` TIMESTAMP NOT NULL  DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE contracts ADD COLUMN isComplete BOOLEAN NOT NULL DEFAULT 0;
ALTER TABLE contracts DROP COLUMN isComplete;
ALTER TABLE contracts ADD COLUMN status VARCHAR(10) NOT NULL DEFAULT 'Performed';
ALTER TABLE contracts DROP COLUMN deadline;
ALTER TABLE contracts ADD COLUMN deadline TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER team_changed_date;
ALTER TABLE contracts CHANGE COLUMN `status` `status` VARCHAR(20) NOT NULL DEFAULT 'Performed';

CREATE TABLE work_positions (
position_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
employee_id INT NOT NULL,
contract_id INT NOT NULL,
FOREIGN KEY(employee_id) REFERENCES employees(employee_id) ON DELETE CASCADE,
FOREIGN KEY(contract_id) REFERENCES contracts(contract_id) ON DELETE CASCADE);

CREATE TABLE generated_employees (
employee_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(30) NOT NULL,
age INT NOT NULL,
salary INT NOT NULL,
performance INT NOT NULL,
description CHAR(255),
company_id INT NOT NULL,
FOREIGN KEY(company_id) REFERENCES companies(company_id) ON DELETE CASCADE);

ALTER TABLE generated_employees ADD COLUMN sex CHAR(6) AFTER age;

CREATE TABLE timings (
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
company_id INT NOT NULL,
employees_generate_timing INT,
contracts_generate_timing INT,
FOREIGN KEY(company_id) REFERENCES companies(company_id) ON DELETE CASCADE);

CREATE TABLE generated_contracts (
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(30) NOT NULL,
performance_units INT NOT NULL,
fee INT NOT NULL,
deadline INT NOT NULL,
description VARCHAR(30),
company_id INT NOT NULL,
FOREIGN KEY(company_id) REFERENCES companies(company_id) ON DELETE CASCADE
)

