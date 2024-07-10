-- Table structure for table employee
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS employees;

CREATE TABLE employee
(
    id         bigint NOT NULL,
    first_name varchar(45) DEFAULT NULL,
    last_name  varchar(45) DEFAULT NULL,
    email      varchar(45) DEFAULT NULL,
    PRIMARY KEY (id)
);

-- Data for table employee
INSERT INTO employee
VALUES (1, 'Naveen', 'Kumar D C', 'naveen@gmail.com'),
       (2, 'Sriraman', 'Parthasarathi', 'sriramab@gmail.com'),
       (3, 'Vijay', 'Perlakota', 'vijay@gmail.com');


select * from employee;