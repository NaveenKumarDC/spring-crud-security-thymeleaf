DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS members;

--
-- Table structure for table members
--

CREATE TABLE members
(
    user_id varchar(50) NOT NULL,
    pw      char(68)    NOT NULL,
    active  int         NOT NULL,
    PRIMARY KEY (user_id)
);

--
-- Inserting data for table members
--
-- NOTE: The passwords are encrypted using BCrypt
--
-- A generation tool is avail at: https://www..com/generate-bcrypt-password
--
-- Default passwords here are: fun123
--

INSERT INTO members
VALUES ('Naveen', '{bcrypt}$2a$10$JCsP1PBI7m6JTKdwjUzDwea5NFxsysq8VFcpb.hRsG74ltHku/0je', 1),
       ('Gowri', '{bcrypt}$2a$10$JCsP1PBI7m6JTKdwjUzDwea5NFxsysq8VFcpb.hRsG74ltHku/0je', 1),
       ('Darshan', '{bcrypt}$2a$10$JCsP1PBI7m6JTKdwjUzDwea5NFxsysq8VFcpb.hRsG74ltHku/0je', 1);


--
-- Table structure for table authorities
--

CREATE TABLE roles
(
    user_id varchar(50) NOT NULL,
    role    varchar(50) NOT NULL,
    CONSTRAINT authorities5_idx_1 UNIQUE (user_id, role),
    CONSTRAINT authorities5_ibfk_1 FOREIGN KEY (user_id) REFERENCES members (user_id)
);

--
-- Inserting data for table roles
--

INSERT INTO roles
VALUES ('Darshan', 'ROLE_EMPLOYEE'),
       ('Gowri', 'ROLE_EMPLOYEE'),
       ('Gowri', 'ROLE_MANAGER'),
       ('Naveen', 'ROLE_EMPLOYEE'),
       ('Naveen', 'ROLE_MANAGER'),
       ('Naveen', 'ROLE_ADMIN');
